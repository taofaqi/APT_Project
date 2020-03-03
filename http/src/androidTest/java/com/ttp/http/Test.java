package com.ttp.http;

/**
 * @author faqi.tao
 * @time 2020/1/13
 */
public class Test {

    public static void main(String[] args) {
        ChainManager chainManager = new ChainManager();
        chainManager.addTask(new Task1());
        chainManager.addTask(new Task2());
        chainManager.addTask(new Task3());

        chainManager.doRealAction("ok", chainManager);
    }
}
