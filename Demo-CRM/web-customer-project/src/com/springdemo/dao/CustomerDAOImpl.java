package com.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	//need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		//get the current hibernate session
		Session currSession = sessionFactory.getCurrentSession();

		//create a query.. sort by last name
		Query<Customer> theQuery = currSession.createQuery("from Customer order by lastName",Customer.class);

		//execute the query and get the result 
		List<Customer> customers = theQuery.getResultList();

		//return the result
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		//get the current session 
		Session currSession = sessionFactory.getCurrentSession();

		//save or update the customer 
		//updates only if the id is != null
		currSession.saveOrUpdate(theCustomer);

	}

	@Override
	public Customer getCustomer(int id) {
		//get the current session 
		Session currSession = sessionFactory.getCurrentSession();

		//return the customer
		Customer customer =  currSession.get(Customer.class, id);

		return customer;


	}

	@Override
	public void deleteCustomer(int id) {

		//get the current session 
		Session currSession = sessionFactory.getCurrentSession();

		Query theQuery=currSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", id);

		theQuery.executeUpdate();

	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {

		//get the current session 
		Session currSession = sessionFactory.getCurrentSession();

		Query theQuery = null;

		
		// only search by name if theSearchName is not empty
		if (theSearchName != null && theSearchName.trim().length() > 0) {

			// search for firstName or lastName ... case insensitive
			theQuery =currSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

		}
		else {
			// theSearchName is empty ... so just get all customers
			theQuery =currSession.createQuery("from Customer", Customer.class);            
		}

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results        
		return customers;

	}





}
