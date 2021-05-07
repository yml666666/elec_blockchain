package org.fisco.bcos.asset.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Int256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Elec extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5061002861002d640100000000026401000000009004565b6101ab565b600061100190508073ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260098152602001807f7269736b5f696e666f0000000000000000000000000000000000000000000000815250602001848103835260078152602001807f6e6f64655f696400000000000000000000000000000000000000000000000000815250602001848103825260258152602001807f6572726f725f726174652c646f776e5f74696d652c6572726f725f6c6576656c81526020017f2c6c6f73730000000000000000000000000000000000000000000000000000008152506040019350505050602060405180830381600087803b15801561016c57600080fd5b505af1158015610180573d6000803e3d6000fd5b505050506040513d602081101561019657600080fd5b81019080805190602001909291905050505050565b6110f0806101ba6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680633b1d036014610051578063fcd7e3c1146100f6575b600080fd5b34801561005d57600080fd5b506100e0600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919080359060200190929190803590602001909291908035906020019092919050505061018f565b6040518082815260200191505060405180910390f35b34801561010257600080fd5b5061015d600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506108bb565b604051808681526020018581526020018481526020018381526020018281526020019550505050505060405180910390f35b60008060008060008093506101a2610fd5565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561020857600080fd5b505af115801561021c573d6000803e3d6000fd5b505050506040513d602081101561023257600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168b6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6e6f64655f696400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b838110156103055780820151818401526020810190506102ea565b50505050905090810190601f1680156103325780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561035257600080fd5b505af1158015610366573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba748a6040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600a8152602001807f6572726f725f726174650000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561041257600080fd5b505af1158015610426573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260098152602001807f646f776e5f74696d65000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b1580156104d257600080fd5b505af11580156104e6573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74886040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f6572726f725f6c6576656c00000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561059257600080fd5b505af11580156105a6573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260048152602001807f6c6f73730000000000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561065257600080fd5b505af1158015610666573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff166331afac368b846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561072557808201518184015260208101905061070a565b50505050905090810190601f1680156107525780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561077257600080fd5b505af1158015610786573d6000803e3d6000fd5b505050506040513d602081101561079c57600080fd5b8101908080519060200190929190505050905060018114156107c157600093506107e5565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff93505b7fe7f59a26199183cce79c754f4eef01ee9f6e7aabe674b0773341289ae7e1df9d848b6000806000806040518087815260200180602001868152602001858152602001848152602001838152602001828103825287818151815260200191508051906020019080838360005b8381101561086c578082015181840152602081019050610851565b50505050905090810190601f1680156108995780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a18394505050505095945050505050565b6000806000806000806000806108cf610fd5565b92508273ffffffffffffffffffffffffffffffffffffffff1663e8434e398a8573ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561095257600080fd5b505af1158015610966573d6000803e3d6000fd5b505050506040513d602081101561097c57600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610a2a578082015181840152602081019050610a0f565b50505050905090810190601f168015610a575780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610a7757600080fd5b505af1158015610a8b573d6000803e3d6000fd5b505050506040513d6020811015610aa157600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610b1857600080fd5b505af1158015610b2c573d6000803e3d6000fd5b505050506040513d6020811015610b4257600080fd5b810190808051906020019092919050505060001415610ba0577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff60008060008084945083935082925081915080905097509750975097509750610fc9565b8173ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b158015610c1057600080fd5b505af1158015610c24573d6000803e3d6000fd5b505050506040513d6020811015610c3a57600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600a8152602001807f6572726f725f7261746500000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610cef57600080fd5b505af1158015610d03573d6000803e3d6000fd5b505050506040513d6020811015610d1957600080fd5b81019080805190602001909291905050508273ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f646f776e5f74696d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610dca57600080fd5b505af1158015610dde573d6000803e3d6000fd5b505050506040513d6020811015610df457600080fd5b81019080805190602001909291905050508373ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000","0000000002815260040180806020018281038252600b8152602001807f6572726f725f6c6576656c000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610ea557600080fd5b505af1158015610eb9573d6000803e3d6000fd5b505050506040513d6020811015610ecf57600080fd5b81019080805190602001909291905050508473ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6c6f737300000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b158015610f8057600080fd5b505af1158015610f94573d6000803e3d6000fd5b505050506040513d6020811015610faa57600080fd5b8101908080519060200190929190505050849450975097509750975097505b50505091939590929450565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f7269736b5f696e666f0000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561107f57600080fd5b505af1158015611093573d6000803e3d6000fd5b505050506040513d60208110156110a957600080fd5b810190808051906020019092919050505090508092505050905600a165627a7a72305820b31508f6ac1a5f9f68df45210c4c6e1443c6cab62d2bab94bc2ce877298786690029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b5061002861002d640100000000026401000000009004565b6101ab565b600061100190508073ffffffffffffffffffffffffffffffffffffffff1663c92a78016040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260098152602001807f7269736b5f696e666f0000000000000000000000000000000000000000000000815250602001848103835260078152602001807f6e6f64655f696400000000000000000000000000000000000000000000000000815250602001848103825260258152602001807f6572726f725f726174652c646f776e5f74696d652c6572726f725f6c6576656c81526020017f2c6c6f73730000000000000000000000000000000000000000000000000000008152506040019350505050602060405180830381600087803b15801561016c57600080fd5b505af1158015610180573d6000803e3d6000fd5b505050506040513d602081101561019657600080fd5b81019080805190602001909291905050505050565b6110f0806101ba6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635b325d7814610051578063c3b7fba5146100ea575b600080fd5b34801561005d57600080fd5b506100b8600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061018f565b604051808681526020018581526020018481526020018381526020018281526020019550505050505060405180910390f35b3480156100f657600080fd5b50610179600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291908035906020019092919080359060200190929190803590602001909291905050506108a9565b6040518082815260200191505060405180910390f35b6000806000806000806000806101a3610fd5565b92508273ffffffffffffffffffffffffffffffffffffffff1663d8ac59578a8573ffffffffffffffffffffffffffffffffffffffff1663c74f8caf6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561022657600080fd5b505af115801561023a573d6000803e3d6000fd5b505050506040513d602081101561025057600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156102fe5780820151818401526020810190506102e3565b50505050905090810190601f16801561032b5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561034b57600080fd5b505af115801561035f573d6000803e3d6000fd5b505050506040513d602081101561037557600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663d3e9af5a6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156103ec57600080fd5b505af1158015610400573d6000803e3d6000fd5b505050506040513d602081101561041657600080fd5b810190808051906020019092919050505060001415610474577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff6000806000808494508393508292508191508090509750975097509750975061089d565b8173ffffffffffffffffffffffffffffffffffffffff16633dd2b61460006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b1580156104e457600080fd5b505af11580156104f8573d6000803e3d6000fd5b505050506040513d602081101561050e57600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff16634900862e6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600a8152602001807f6572726f725f7261746500000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b1580156105c357600080fd5b505af11580156105d7573d6000803e3d6000fd5b505050506040513d60208110156105ed57600080fd5b81019080805190602001909291905050508273ffffffffffffffffffffffffffffffffffffffff16634900862e6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f646f776e5f74696d650000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561069e57600080fd5b505af11580156106b2573d6000803e3d6000fd5b505050506040513d60208110156106c857600080fd5b81019080805190602001909291905050508373ffffffffffffffffffffffffffffffffffffffff16634900862e6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600b8152602001807f6572726f725f6c6576656c000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561077957600080fd5b505af115801561078d573d6000803e3d6000fd5b505050506040513d60208110156107a357600080fd5b81019080805190602001909291905050508473ffffffffffffffffffffffffffffffffffffffff16634900862e6040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260048152602001807f6c6f737300000000000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561085457600080fd5b505af1158015610868573d6000803e3d6000fd5b505050506040513d602081101561087e57600080fd5b8101908080519060200190929190505050849450975097509750975097505b50505091939590929450565b60008060008060008093506108bc610fd5565b92508273ffffffffffffffffffffffffffffffffffffffff16635887ab246040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561092257600080fd5b505af1158015610936573d6000803e3d6000fd5b505050506040513d602081101561094c57600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff16631a391cb48b6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6e6f64655f696400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610a1f578082015181840152602081019050610a04565b50505050905090810190601f168015610a4c5780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610a6c57600080fd5b505af1158015610a80573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663def426988a6040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600a8152602001807f6572726f725f726174650000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610b2c57600080fd5b505af1158015610b40573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663def42698896040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260098152602001807f646f776e5f74696d65000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610bec57600080fd5b505af1158015610c00573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663def42698886040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f6572726f725f6c6576656c00000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610cac57600080fd5b505af1158015610cc0573d6000803e3d6000fd5b505050508173ffffffffffffffffffffffffffffffffffffffff1663def42698876040518263ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001838152602001828103825260048152602001807f6c6f73730000000000000000000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610d6c57600080fd5b505af1158015610d80573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16634c6f30c08b846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b83811015610e3f578082015181840152602081019050610e24565b505050509050","90810190601f168015610e6c5780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610e8c57600080fd5b505af1158015610ea0573d6000803e3d6000fd5b505050506040513d6020811015610eb657600080fd5b810190808051906020019092919050505090506001811415610edb5760009350610eff565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff93505b7fe2bf4d3e894a29abc5bd31c94bc138e9597db35d782f611750c787f0286f7029848b6000806000806040518087815260200180602001868152602001858152602001848152602001838152602001828103825287818151815260200191508051906020019080838360005b83811015610f86578082015181840152602081019050610f6b565b50505050905090810190601f168015610fb35780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a18394505050505095945050505050565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff166359a48b656040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260098152602001807f7269736b5f696e666f0000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561107f57600080fd5b505af1158015611093573d6000803e3d6000fd5b505050506040513d60208110156110a957600080fd5b810190808051906020019092919050505090508092505050905600a165627a7a723058209405a8f3cc571101d30ebc21c84b047ba8299d80ee5eefe2e9d6a9db7bc027880029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"node_id\",\"type\":\"string\"},{\"name\":\"error_rate\",\"type\":\"int256\"},{\"name\":\"down_time\",\"type\":\"int256\"},{\"name\":\"error_level\",\"type\":\"int256\"},{\"name\":\"loss\",\"type\":\"int256\"}],\"name\":\"register\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"node_id\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"node_id\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"error_rate\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"down_time\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"temp_error_level\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"loss\",\"type\":\"int256\"}],\"name\":\"RegisterEvent\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_SELECT = "select";

    public static final Event REGISTEREVENT_EVENT = new Event("RegisterEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}));
    ;

    protected Elec(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt register(String node_id, BigInteger error_rate, BigInteger down_time, BigInteger error_level, BigInteger loss) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(node_id), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(error_rate), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(down_time), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(error_level), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(loss)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void register(String node_id, BigInteger error_rate, BigInteger down_time, BigInteger error_level, BigInteger loss, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(node_id), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(error_rate), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(down_time), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(error_level), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(loss)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegister(String node_id, BigInteger error_rate, BigInteger down_time, BigInteger error_level, BigInteger loss) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(node_id), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(error_rate), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(down_time), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(error_level), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Int256(loss)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> getRegisterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue()
                );
    }

    public Tuple1<BigInteger> getRegisterOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> select(String node_id) throws ContractException {
        final Function function = new Function(FUNC_SELECT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(node_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                (BigInteger) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue());
    }

    public List<RegisterEventEventResponse> getRegisterEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEREVENT_EVENT, transactionReceipt);
        ArrayList<RegisterEventEventResponse> responses = new ArrayList<RegisterEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.node_id = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.error_rate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.down_time = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.temp_error_level = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.loss = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeRegisterEventEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(REGISTEREVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeRegisterEventEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(REGISTEREVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static Elec load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Elec(contractAddress, client, credential);
    }

    public static Elec deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Elec.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class RegisterEventEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger ret;

        public String node_id;

        public BigInteger error_rate;

        public BigInteger down_time;

        public BigInteger temp_error_level;

        public BigInteger loss;
    }
}
