/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalinventory;


import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.plaf.RootPaneUI;
import javax.swing.JOptionPane.*;

/**
 *
 * @author Nath
 */
public class submitInfo {
    Connection con;
    private String ProductDesc;
    private String ProductCode;
    private double NumOfDays;
    private double CostPerDay;
    private String customerName;
    private String customerEmail;
    private String customerPhoneno;
    private String customerAddress;
    private int credit;
    private int dueDays;
    private int discount;
    private int paymentDueDays;
    private double deposit =0.0;
    private double tax;
    private double subTotal;
    private double total;
    int rscu2,rspro2;
    int selid;
    String selprodname,selprodcode;
    double selprodcostday,selprodnumday;
            
    
    protected void SetProductInfo(String ProductDes,String ProductCod,double numofday,double costperday){
        this.ProductDesc = ProductDes;
        this.ProductCode = ProductCod;
        this.CostPerDay = costperday;
        this.NumOfDays = numofday;
    }
    
    
    
    
    protected void StoreProdInfo(){
        
    try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://localhost/rental_inv?user=root");
        PreparedStatement selProd = con.prepareStatement("SELECT `product_id`, `product_name`, `product_code`, `product_cost`, `days` FROM `product_table` WHERE `product_id`=(SELECT COUNT(*) FROM `product_table`) ");
        PreparedStatement pstm = con.prepareStatement("INSERT INTO `prod_tbl`(`prodid`, `prod_name`, `prod_code`, `prod_cost`, `days`, `customer_id`, `prodinfo_col`) VALUES ( ?,?,?,?,?,?,?)");
        PreparedStatement custID= con.prepareStatement("SELECT `customerID` FROM `customer_table` WHERE `customerID`=(SELECT MAX(customerID) FROM `customer_table`)");
        PreparedStatement prodID = con.prepareStatement("SELECT `prodID` FROM `prodpay_tinfo` WHERE `prodID`=(SELECT MAX(prodID) FROM `prodpay_tinfo`)");
        
        ResultSet rscu = custID.executeQuery();
        ResultSet rsprod = selProd.executeQuery();
        ResultSet rspro = prodID.executeQuery();
        if(rscu.next() && rspro.next() && rsprod.next()){
            rscu2 = rscu.getInt("customerID");
            rspro2 = rspro.getInt("prodID");
            selid = rsprod.getInt("product_id");
            selprodname = rsprod.getString("product_name");
            selprodcode =rsprod.getString("product_code");
            selprodcostday =rsprod.getDouble("product_cost");
            selprodnumday = rsprod.getDouble("days");
        }
        
        pstm.setInt(1, 0);
        pstm.setString(2, selprodname);
        pstm.setString(3,selprodcode);
        pstm.setDouble(4, selprodcostday);
        pstm.setDouble(5, selprodnumday);
        pstm.setInt(6, rscu2);
        pstm.setInt(7, rspro2 );
        pstm.execute();
        
        JOptionPane.showMessageDialog(null, "Record Saved");
        con.close();

    }catch(SQLException sql){
        JOptionPane.showMessageDialog(null,sql+" fkERROR");
        }catch(Exception e){
        JOptionPane.showMessageDialog(null,e+" fkERROR");
        
    }
    
    }
    
    protected void StoreProdInfo1(){
    try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://localhost/rental_inv?user=root");
        //&password=6247
        //PreparedStatement pstm = con.prepareStatement("INSERT INTO prod_tbl(prodid,prod_name,prod_code,prod_cost,days,customer_id,prodinfo_col)VALUES(?,?,?,?,?,?,?) ");
        //INSERT INTO `prod_tbl`(`prodid`, `prod_name`, `prod_code`, `prod_cost`, `days`, `customer_id`, `prodinfo_col`) VALUES ( 2,"hhf","hskjsh",2000.00,5, (SELECT customer_table.customerID FROM customer_table WHERE customer_table.customerID=2), (SELECT prodpay_tinfo.prodID FROM prodpay_tinfo WHERE prodpay_tinfo.prodID=2) );
        PreparedStatement pstm = con.prepareStatement("INSERT INTO `product_table`(`product_id`, `product_name`, `product_code`, `product_cost`, `days`) VALUES (?,?,?,?,?)");
        
        
        pstm.setInt(1, 0);
        pstm.setString(2, ProductDesc);
        pstm.setString(3,ProductCode);
        pstm.setDouble(4, CostPerDay);
        pstm.setDouble(5, NumOfDays);
        
        pstm.execute();
       JOptionPane.showMessageDialog(null, "Total Gotten");
       
        
        
        
        con.close();

    }catch(SQLException sql){
        JOptionPane.showMessageDialog(null,sql);
        }catch(Exception e){
        JOptionPane.showMessageDialog(null,e+" fkERROR");
        
    }
    
    }
      
        
    protected void SetCustomerInfo(String name,String Address,String Phoneno,String email) {
    this.customerName = name;
    this.customerAddress = Address;
    this.customerPhoneno = Phoneno;
    this.customerEmail = email;
    }
    protected  void StoreCusInfo(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://localhost/rental_inv?user=root");
        //&password=6247
        PreparedStatement pstm = con.prepareStatement("INSERT INTO customer_table(customerID,cust_name,customer_address,customer_phoneno,customer_email)VALUES(?,?,?,?,?)");
        pstm.setInt(1, 0);
        pstm.setString(2, customerName);
        pstm.setString(3,customerAddress);
        pstm.setString(4, customerPhoneno);
        pstm.setString(5, customerEmail);
        pstm.execute();
        con.close();
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e+"CusTBL");
    } 
    }
    
    protected void SetProdPayInfo (int creditl,int dueday,int discounts,int paymentdueday,double tas,double subtotal,double totalled){
    
        this.credit = creditl;
        this.dueDays = dueday;
        this.discount =discounts;
        this.paymentDueDays = paymentdueday;
        //this.deposit = deposits;
        this.tax = tas;
        this.subTotal = subtotal;
        this.total = totalled;
    }
    
    
    
    
    protected void StorePaymentInfo(){
       try {
           Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://localhost/rental_inv?user=root");
        //&password=6247
        PreparedStatement pstm = con.prepareStatement("INSERT INTO prodpay_tinfo(prodID,CreditLimit,SetDueDays,Discount,PayDueDay,Deposit_Paid,Tax,SubTotal,Total)VALUES(?,?,?,?,?,?,?,?,?) ");
        pstm.setInt(1, 0);
        pstm.setInt(2,credit);
        pstm.setInt(3, dueDays);
        pstm.setInt(4,discount);
        pstm.setInt(5, paymentDueDays);
        pstm.setDouble(6, deposit);
        pstm.setDouble(7, tax);
        pstm.setDouble(8, subTotal);
        pstm.setDouble(9,total);
        pstm.execute();
        con.close();
        
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    } 
       }
    
    
    }
    

