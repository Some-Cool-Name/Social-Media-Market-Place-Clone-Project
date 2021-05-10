<?php
require "db_connection.php";

/*
uses one update statement to update User_Account and Description.
so when you update profile, you expect to receive all the current user info from the app
i.e bio, fullname, birthday, gender, sexuality, useremail.
*/

if(isset($_REQUEST["useremail"]) && isset($_REQUEST["fullname"])
 && isset($_REQUEST["birthday"]) && isset($_REQUEST["gender"])
 && isset($_REQUEST["sexuality"]) && isset($_REQUEST["bio"]))
 {

  /* deals with User_Account table*/
  $useremail = $_REQUEST["useremail"]; // this is a primary key and a foreign key to other table
  //$newemail = $_REQUEST["newemail"];
  $fullname = $_REQUEST["fullname"];
  $birthday = $_REQUEST["birthday"];
  $gender = $_REQUEST["gender"];
  $sexuality = $_REQUEST["sexuality"];

  /* deals with Description  */
  $bio =  $_REQUEST["bio"];

  $sqlQuery = "UPDATE User_Accounts, Description
  SET User_Accounts.Full_Name = '$fullname',
  User_Accounts.Birthday = '$birthday',
  User_Accounts.Gender = '$gender',
  User_Accounts.Sexuality = '$sexuality',
  Description.Bio = '$bio'
  WHERE User_Accounts.E_mail='$useremail' AND Description.E_mail='$useremail'";

  $result = mysqli_query($db_conn, $sqlQuery);

  if(mysqli_affected_rows($db_conn) == 1 ){
      echo json_encode(["message"=>"success"]);
  }else{
      echo json_encode(["message"=>"update failed".mysqli_error($db_conn)]);
  }

}
else{
  echo json_encode(["message"=>"some fields are empty"]);
}

?>
