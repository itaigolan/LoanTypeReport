package com.loanapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.loanapp.beans.Loan;

public class loanDao {

	private JdbcTemplate template;
	
	//Default constructor
	public loanDao() {}
	
	public loanDao(JdbcTemplate template) {
		this.template = template;
	}
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	//Goes through the result query obtained from the sql query
	//Puts each row into a loan object in a list
	public List<Loan> queryLoans(String sqlStatement){
		return template.query(sqlStatement,new RowMapper<Loan>(){  
			    public Loan mapRow(ResultSet rs, int rownumber) throws SQLException {  
			        Loan l =new Loan();  

			        //Sets the values of the loan from the ResultSet values
			        l.setLoanID(rs.getInt("loan_id"));
			        l.setLoanType(rs.getInt("loan_type"));
					l.setCustomerID(rs.getInt("customer_id"));
			        l.setAmount(rs.getInt("amount"));
			        l.setStartDate(rs.getString("start_date"));
			        l.setLoanDuration(rs.getInt("loan_duration"));
			        l.setInterestRate(rs.getInt("interest_rate"));
			        l.setLoanStatus(rs.getString("loan_status"));
			        l.setReviewStatus(rs.getString("review_status"));
			        l.setDownPayment(rs.getInt("down_payment"));
			        
			        return l;  
			    }  
		});
		
	}
	
}
