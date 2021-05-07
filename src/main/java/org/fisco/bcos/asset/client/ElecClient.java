package org.fisco.bcos.asset.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fisco.bcos.asset.contract.Elec;
import org.fisco.bcos.asset.entity.ErrorResultInfo;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;


public class ElecClient {
    static Logger logger = LoggerFactory.getLogger(ElecClient.class);

    private BcosSDK bcosSDK;
    private Client client;
    private CryptoKeyPair cryptoKeyPair;

    public void initialize() throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
    }

    public void deployAssetAndRecordAddr() {

        try {
            Elec elec = Elec.deploy(client, cryptoKeyPair);
            System.out.println(
                    " deploy Asset success, contract address is " + elec.getContractAddress());

            recordAssetAddr(elec.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy contract failed, error message is  " + e.getMessage());
        }
    }

    public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    public String loadAssetAddr() throws Exception {
        // load Asset contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load Asset contract address failed, please deploy it first. ");
        }
        logger.info(" load Asset address from contract.properties, address is {}", contractAddress);
        return contractAddress;
    }

    public ErrorResultInfo query(String nodeId) {
        ErrorResultInfo errorResultInfo = new ErrorResultInfo(nodeId, 0, 0, 0, 0);
        try {
            System.out.printf("start!!!");
            String contractAddress = loadAssetAddr();
            //            System.out.printf("load success"+contractAddress);
            Elec elec = Elec.load(contractAddress, client, cryptoKeyPair);
            System.out.printf("load success" + contractAddress);
            //            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
            Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> result = elec.select(nodeId);
            //            System.out.printf("result"+result.toString());
            if (result.getValue1().compareTo(new BigInteger("0")) == 0) {
                System.out.printf(" Elec nodeId %s, error_rate %s \n", nodeId, result.getValue2());
                System.out.printf(" Elec nodeId %s, down_time %s \n", nodeId, result.getValue3());
                System.out.printf(" Elec nodeId %s, error_level %s \n", nodeId, result.getValue4());
                System.out.printf(" Elec nodeId %s, loss %s \n", nodeId, result.getValue5());
                errorResultInfo.setErrorLevel(result.getValue4().intValue());
                errorResultInfo.setNodeId(nodeId);
                errorResultInfo.setErrorRate(result.getValue2().intValue());
                errorResultInfo.setErrorTime(result.getValue3().intValue());
                errorResultInfo.setLoss(result.getValue3().intValue());
            } else {
                System.out.printf(" %s Elec nodeId is not exist \n", nodeId);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();-
            logger.error(" query exception, error message is {}", e.getMessage());

            System.out.printf(" query failed, error message is %s\n", e.getMessage());
        }
        return errorResultInfo;
    }

    public void registerInfo(String nodeId, BigInteger errorRate, BigInteger downTime, BigInteger errorLevel,
            BigInteger loss) {
        try {
            String contractAddress = loadAssetAddr();
            Elec elec = Elec.load(contractAddress, client, cryptoKeyPair);
            //            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = elec.register(nodeId, errorRate, downTime, errorLevel, loss);
            System.out.println("recipt:" + receipt);
            List<Elec.RegisterEventEventResponse> response = elec.getRegisterEventEvents(receipt);
            System.out.println("response:" + response);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
                    System.out.printf(
                            " register nodeId success => nodeId: %s, errorRate: %s, downTime: %s, errorLevel: %s \n",
                            nodeId, errorRate, downTime, errorLevel);
                } else {
                    System.out.printf(
                            " register nodeId failed, ret code is %s \n", response.get(0).ret.toString());
                }
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();

            logger.error(" registerInfo exception, error message is {}", e.getMessage());
            System.out.printf(" register registerInfo failed, error message is %s\n", e.getMessage());
        }
    }

    public void riskTrace(String[] nodeIds) {
        List<String> resNodeIdsList = new ArrayList<>();
        List<Integer> resLevelList = new ArrayList<>();
        List<Double> resErrorRateList = new ArrayList<>();
        List<Double> resLossList = new ArrayList<>();
        List<Integer> resTimeList = new ArrayList<>();
        for (int i = 0; i < nodeIds.length; i++) {
            ErrorResultInfo info = query(nodeIds[i]);
            if (info.getErrorLevel() > 1) {
                resNodeIdsList.add(info.getNodeId());
                resLevelList.add(info.getErrorLevel());
                resErrorRateList.add((double) Math.round(info.getErrorRate()) / 1000000);
                resLossList.add((double) Math.round(info.getLoss()) / 100);
                resTimeList.add(info.getErrorTime());

            }

        }
        Table showTable = Table.create("电力系统节点风险事故报告")
                .addColumns(StringColumn.create("电力系统节点名称", resNodeIdsList.stream().toArray(String[]::new)),
                        DoubleColumn.create("节点风险概率", resErrorRateList.stream().toArray(Double[]::new)),
                        IntColumn.create("节点故障导致电网停运时间", resTimeList.stream().toArray(Integer[]::new)),
                        DoubleColumn.create("节点导致负荷损失", resLossList.stream().toArray(Double[]::new)),
                        IntColumn.create("节点风险等级", resLevelList.stream().toArray(Integer[]::new)));
        System.out.println(showTable.print());
    }


    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.ElecClient deploy");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.ElecClient query");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.ElecClient registerInfo");
        //        System.out.println(
        //                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.ElecClient transfer
        //                from_account to_account amount");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            Usage();
        }

        ElecClient client = new ElecClient();
        client.initialize();
        //        client.deployAssetAndRecordAddr();
        //        client.registerAssetAccount("A", new BigInteger("1000"));
        //        client.queryAssetAmount("A");

        switch (args[0]) {
            case "deploy":
                client.deployAssetAndRecordAddr();
                break;
            case "query":
                if (args.length < 2) {
                    Usage();
                }
                client.query(args[1]);
                break;
            case "register":
                if (args.length < 6) {
                    Usage();
                }
                client.registerInfo(args[1], new BigInteger(args[2]), new BigInteger(args[3]), new BigInteger(args[4]),
                        new BigInteger(args[5]));
                break;
            case "trace":
                List<String> nodeList = new ArrayList<>();
                nodeList.add(args[1]);
                nodeList.add(args[2]);
                nodeList.add(args[3]);
                nodeList.add(args[4]);
                nodeList.add(args[5]);
                client.riskTrace(nodeList.stream().toArray(String[]::new));
                break;
            default: {
                Usage();
            }
        }
        System.exit(0);
    }

}