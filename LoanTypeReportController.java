package com.loanapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loanapp.beans.DBProperties;
import com.loanapp.beans.Loan;
import com.loanapp.dao.loanDao;

@Controller
public class LoanTypeReportController extends HttpServlet {
	
	@Autowired
	DBProperties properties; //Creates DBProperties object and initializes its properties from the properties file
	JdbcTemplate template;
	
	@RequestMapping("/loanReport")
	
	//Makes the loan report
	public void loanReport(HttpServletRequest req, HttpServletResponse response) {
		
		//Gets the loan type, start date and end date from LoanTypeReport.jsp
		Date startDate = new Date(Integer.parseInt(req.getParameter("StartMonth")),Integer.parseInt(req.getParameter("StartDay")),
				Integer.parseInt(req.getParameter("StartYear")));
		
		Date endDate = new Date(Integer.parseInt(req.getParameter("EndMonth")),Integer.parseInt(req.getParameter("EndDay")),
				Integer.parseInt(req.getParameter("EndYear")));
		
		String loanType = req.getParameter("SelectLoan");		
				
		//Instantiates template to the DB connection in properties
		template = properties.jdbcTemplate(properties.datasource());
		
		loanDao accesser = new loanDao(template);
		
		String loanQuery = "Select loan_id From loan_app_loan_types where loan_type = " + loanType + ";";
		
		String sqlStatement = "Select * From loan_app_loans where (loan_id = " + loanQuery + ")";
		sqlStatement += "And start_date Between " + startDate + " And " + endDate + ";";
		
		//Runs the query and gets the results
		List<Loan> loans = accesser.queryLoans(sqlStatement);
		
		//Keeps information on database query
        HttpSession session = req.getSession();
        session.setAttribute("Query",loans);
		
		try (PrintWriter out = response.getWriter()) {
			out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ReportController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>You must check either the Customer button or the Employee button</h1>");
            while(!loans.isEmpty()) {
            	Loan l = loans.get(0);
            	loans.remove(0);
            	out.println("<p>" +l.toString() + "</p>");
            }
            out.println("</body>");
            out.println("</html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
