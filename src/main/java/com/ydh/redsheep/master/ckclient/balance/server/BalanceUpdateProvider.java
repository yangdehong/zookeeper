package com.ydh.redsheep.master.ckclient.balance.server;

public interface BalanceUpdateProvider {
	
	boolean addBalance(Integer step);
	
	boolean reduceBalance(Integer step);

}
