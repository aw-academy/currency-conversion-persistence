package de.awacademy.currency_conversion_persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 */
public class InitialisePersistenceDbTest {

	private static final String dbUser = "root";
	private static final String dbPassword = "root";
	private static final String dbName = "currency_conversion_db";
	
	private static final String myPocket = "ec447f09-66ae-454a-bf56-770a720e63de";
	private static final String usdPocket = "fc447f09-66ae-454a-bf56-770a720e63df";
	

	private final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("PERSISTENCE_UNIT");
	private final EntityManager entityManager = entityManagerFactory.createEntityManager();

	
	@BeforeClass
	public static void setUp() throws SQLException {
		Connection connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/?user=" + dbUser + "&password=" + dbPassword);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
	}
	

	@Test
	public void createAndInsertSecretsAndMoneys() throws SQLException {
		Random random = new Random();

		System.out.print("Creating ");
		
		entityManager.getTransaction().begin();

		createWallets(random);
		myWallet();
		createWallets(random);
		usdWallet();
		createWallets(random);
		
		entityManager.getTransaction().commit();

		System.out.print(" Done");
		System.out.println();
	}

	
	private void usdWallet() {
		Wallet wallet = new Wallet();
		wallet.setPocket(usdPocket);
		
		Money money = new Money();
		money.setCurrency("USD");
		money.setValue(12);
		entityManager.persist(money);
		wallet.getMoneys().add(money);

		entityManager.persist(wallet);
		System.out.print(".");
	}

	private void myWallet() {
		Wallet myWallet = new Wallet();
		myWallet.setPocket(myPocket);
		
		Money m1 = new Money();
		m1.setCurrency("EUR");
		m1.setValue(10);
		entityManager.persist(m1);
		myWallet.getMoneys().add(m1);

		Money m2 = new Money();
		m2.setCurrency("EUR");
		m2.setValue(15);
		entityManager.persist(m2);
		myWallet.getMoneys().add(m2);

		Money m3 = new Money();
		m3.setCurrency("EUR");
		m3.setValue(17);
		entityManager.persist(m3);
		myWallet.getMoneys().add(m3);

		entityManager.persist(myWallet);
		System.out.print(".");
	}

	
	private void createWallets(Random random) {
		int walletCount = Math.abs(random.nextInt()) % 50;
		for (int j = 0; j < walletCount; j++) {

			Wallet wallet = new Wallet();
			wallet.setPocket(UUID.randomUUID().toString());

			int moneyCount = Math.abs(random.nextInt()) % 100;
			for (int i = 0; i < moneyCount; i++) {
				
				Money money = new Money();
				money.setCurrency("EUR");
				money.setValue(Math.abs(random.nextInt()) % 100);

				entityManager.persist(money);

				wallet.getMoneys().add(money);
			}
			
			System.out.print(".");
			
			entityManager.persist(wallet);
		}
	}

}
