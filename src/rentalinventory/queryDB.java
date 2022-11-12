/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rentalinventory;

/**
 *
 * @author Nath
 */
import java.sql.*;
import javax.swing.JOptionPane;

public class queryDB {
    private String cust_name;
    private String cust_Address;
    private String cust_PhoneNum;
    private String cust_email;
    
    private String Prod_Name;
    private double Prod_Cost;
    private String Prod_code;
    private int days;
    String content;
    private int Prod_Discount;
    
    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    String [] DBCONTENT;
    
    //Setter methods declaration For Customer
    
    protected void SetCustName(String cusName){
        this.cust_name = cusName;
    }
    protected void SetCusAdd(String cusAddr){
        this.cust_Address = cusAddr;
    }
    protected void SetCusPhoneNum(String cusPhn){
        this.cust_PhoneNum = cusPhn;
    }
    protected void SetCusEmail(String cusEmail){
        this.cust_email = cusEmail;
    }
    
    //Getter Method Declaration For Customer
    
    protected String getCustName(){
        return this.cust_name;
    }
    protected String getCusAdd(){
        return this.cust_Address;
    }
    protected String getCusPhn(){
        return this.cust_PhoneNum;
    }
    protected String getCusEmail(){
        return this.cust_email;
    }
    
        //Setter Method Declaration for Product Info
    
    protected void SetProdName(String prodname){
        this.Prod_Name = prodname;
    }
    protected void SetProd_Cost(Double productcost){
        this.Prod_Cost = productcost;
    }
    protected void SetProd_Code(String productcode){
        this.Prod_code = productcode;
    }
    protected void SetDays(int daysUsed){
        this.days = daysUsed;
    }
    
        //Getter Method Declaration For Product Info
    
    protected String getProdName(){
        return this.Prod_Name;
    }
    protected Double getProd_Cost(){
       return this.Prod_Cost;
    }
    protected String getProd_Code(){
        return this.Prod_code;
    }
    protected int getDays(){
        return this.days;
    }
    
    
      //Setter Method Declaration For The Product Payment Info
    
    protected void SetProdDiscount(int ProductDiscount){
        this.Prod_Discount = ProductDiscount;
    }
    
      //Getter Method Declaration For The Product Payment Info 
    
    protected int GetprodDiscount(){
        return this.Prod_Discount;
    }
    
    
    
    protected void connectDB(){
        try{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection("jdbc:mysql://localhost/rental_inv?user=root&password=");
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    }
    
    protected String LoadData(String productname){
    connectDB();
    try{
        //prod_name,prod_code,prod_cost,days,customer_table.cust_name,customer_table.customer_address,customer_table.customer_phoneno,customer_table.customer_email,prodpay_tinfo.Discount
        pstm=con.prepareStatement("SELECT prod_name,prod_code,prod_cost,days,customer_table.cust_name,customer_table.customer_address,customer_table.customer_phoneno,customer_table.customer_email,prodpay_tinfo.Discount From prod_tbl INNER JOIN customer_table ON prod_tbl.prodid=customer_table.customerID INNER JOIN prodpay_tinfo ON prod_tbl.prodid=prodpay_tinfo.prodID WHERE prod_name=?;");
     pstm.setString(1, productname);
        /*pstm=con.prepareStatement("Select cust_name,cust_address,cust_phoneno,cust_email FROM `customer_table` WHERE cust_name=?");
    pstm.setString(1, costumer);
    pstm=con.prepareStatement("Select prod_name,prod_code,prod_cost,days FROM `prod_tbl` WHERE prod_name=?");
    pstm.setString(1, productname);
    pstm=con.prepareStatement("Select Discount FROM `prodpay_tinfo` WHERE Discount=?");
    pstm.setString(1, productinfo);*/
    rs=pstm.executeQuery();
    while(rs.next()){
        
        String custName=rs.getString("customer_table.cust_name");
        String custAddress=rs.getString("customer_table.customer_address");
        String custNumb=rs.getString("customer_table.customer_phoneno");
        String custEmail=rs.getString("customer_table.customer_email");
        String probName=rs.getString("prod_name");
        String prodCode=rs.getString("prod_code");
        String prodCost=rs.getString("prod_cost");
        String days=Integer.toString(rs.getInt("days"));
        String distcount=Integer.toString(rs.getInt("prodpay_tinfo.Discount"));
         
        content="Customer Name:"+custName+"\n\nCustomer Adderess:"+custAddress+"\n\nCustomer Phone Number:"+custNumb
        +"\n\nCustomer Email:"+custEmail+"\n\nProduct Name:"+probName+"\n\nProduct Code:"+prodCode+"\n\nProduct Cost:"+"$"+prodCost
        +"\n\nDays:"+days+"\n\nDiscount:"+distcount+"%"+"\n";
    }        
    }catch(SQLException sqle){
            JOptionPane.showMessageDialog(null, sqle);
    }
    return content ;
    }
    
}
