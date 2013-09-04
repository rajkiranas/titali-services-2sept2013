package com.quick.tim.mobileserviceprovider.DAOImpl;

import com.quick.tim.mobileserviceprovider.DAO.TransactionBo;
import org.springframework.stereotype.Repository;

@Repository("transactionBo")

public class TransactionBoImpl implements TransactionBo {

	public String save() {

		return "Jersey + Spring example";

	}

}