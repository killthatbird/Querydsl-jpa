package com.tja;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mysema.query.jpa.impl.JPAQuery;
import com.tja.domain.Address;
import com.tja.domain.Customer;
import com.tja.domain.EmailAddress;
import com.tja.domain.QCustomer;


public class QuerydslTest {

	//private List<Customer> customers = new ArrayList<Customer>();
	private static EntityManagerFactory factory = null; 
	
	private static EntityManager entityManager = null;
	
	@BeforeClass
	public  static void init() {
		factory = Persistence.createEntityManagerFactory("myJPA");  
		entityManager = factory.createEntityManager(); 
		
	          //构造集合数据
  		for(int i = 0 ;i<10;i++) {
  			entityManager.getTransaction().begin();   //开启事务
  			Customer c = new Customer();
  			c.setFirstName("firstName-"+i);
  			c.setLastName("lastName-"+i);
  			
  			EmailAddress email = new EmailAddress();
  			email.setEmail("lean-"+i+"@l63.com");
  			c.setEmailAddress(email);
  			
  			Set<Address> ads = new HashSet<Address>();
  			Address a1 = new Address();
  			a1.setCite("cite-"+i);
  			a1.setStreet("street-"+i);
  			a1.setCustomer(c);
  			
  			Address a2 = new Address();
  			a2.setCite("cite-copy-"+i);
  			a2.setStreet("street-copy-"+i);
  			a2.setCustomer(c);
  			ads.add(a1);
  			ads.add(a2);
  			c.setAddresses(ads);
  			//customers.add(c);
  			entityManager.persist(c);
  			entityManager.getTransaction().commit();    //提交事务
  		}
  	}
	
	@AfterClass
	public static void destroy() {
		factory.close();
	}
	
	
	@Test
   public void testQuery1() {
		QCustomer $ = QCustomer.customer;
		JPAQuery query = new JPAQuery(entityManager);
		List<Long> result = query.from($).list($.id);
		Assert.assertEquals(result.size(), 10);
   }
   
	@Test
	   public void testQuery2() {
			QCustomer $ = QCustomer.customer;
			JPAQuery query = new JPAQuery(entityManager);
			List<String> result = query.from($).where($.emailAddress.email.contains("7")).list($.firstName);
			Assert.assertEquals(result.size(), 1);
			Assert.assertEquals("firstName-7", result.get(0));
	   }
	
	 @Test
	   public void testQuery3() {
			QCustomer $ = QCustomer.customer;
			JPAQuery query = new JPAQuery(entityManager);
			List<Long> result = query.from($).where($.addresses.size().eq(2)).list($.id);
			Assert.assertEquals(10, result.size());
	   }
}
