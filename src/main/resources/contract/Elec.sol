pragma solidity ^0.4.25;

import "./Table.sol";

contract Elec {
    // event
    event RegisterEvent(int256 ret, string node_id, int256 error_rate,int256 down_time,int256 temp_error_level,int256 loss);
    // event TransferEvent(int256 ret, string from_account, string to_account, uint256 amount);

    //    string[] public error_node;

    constructor() public {
        // 构造函数中创建t_asset表
        createTable();
    }

    function createTable() private {
        TableFactory tf = TableFactory(0x1001);
        // 资产管理表, key : account, field : asset_value
        // |  资产账户(主键)      |     资产金额       |
        // |-------------------- |-------------------|
        // |        account      |    asset_value    |
        // |---------------------|-------------------|
        //
        // 创建表
        tf.createTable("risk_info", "node_id", "error_rate,down_time,error_level,loss");
    }

    function openTable() private returns(Table) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("risk_info");
        return table;
    }

    /*
    描述 : 根据资产账户查询资产金额
    参数 ：
            account : 资产账户

    返回值：
            参数一： 成功返回0, 账户不存在返回-1
            参数二： 第一个参数为0时有效，资产金额
    */
    function select(string node_id) public constant returns(int256,int256,int256,int256,int256) {
        // 打开表
        Table table = openTable();
        // 查询
        Entries entries = table.select(node_id, table.newCondition());
        if (0 == uint256(entries.size())) {
            return (-1, 0 ,0,0,0);
        } else {
            Entry entry = entries.get(0);
            return (0, entry.getInt("error_rate"),entry.getInt("down_time"),entry.getInt("error_level"),entry.getInt("loss"));
        }
    }

    /*
    描述 : 资产注册
    参数 ：
            account : 资产账户
            amount  : 资产金额
    返回值：
            0  资产注册成功
            -1 资产账户已存在
            -2 其他错误
    */
    function register(string node_id, int256 error_rate,int256 down_time,int256 error_level,int256 loss) public returns(int256){
        int256 ret_code = 0;
        Table table = openTable();
        Entry entry = table.newEntry();
        entry.set("node_id", node_id);
        entry.set("error_rate", error_rate);
        entry.set("down_time", down_time);
        entry.set("error_level", error_level);
        entry.set("loss",loss);
        // 插入
        int count = table.insert(node_id, entry);
        if (count == 1) {
            // 成功
            ret_code = 0;
        } else {
            // 失败? 无权限或者其他错误
            ret_code = -1;
        }
        emit RegisterEvent(ret_code, node_id, 0,0,0,0);
        return ret_code;
    }


}