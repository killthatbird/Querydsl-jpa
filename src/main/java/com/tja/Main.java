package com.tja;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/**  add some comment to test */
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJPA");  
         factory.close();
	}

}
