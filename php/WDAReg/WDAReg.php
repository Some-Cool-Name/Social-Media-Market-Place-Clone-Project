<?php
    $conn = mysqli_connect("127.0.0.1", "s1851427", "78714377", "d1851427");
    //User_Accounts
        $Email = $_REQUEST['username'];
        $Password = $_REQUEST['password'];
        $Full_Name = $_REQUEST['name'];
        $Gender = $_REQUEST['gender'];
        $Birthday = $_REQUEST['birthday'];
        $Sexuality = $_REQUEST['sexuality'];
    
    //Location
        //$Email 
        $Location=$_REQUEST['location'];
    
    
    //Account_links
        //$Email = $_REQUEST['username'];
        /*$Facebook_link = $_REQUEST['Facebook_link'];
        $Instagram_link = $_REQUEST['instagram_link'];
        $Twitter_link = $_REQUEST['twitter_link'];*/
        

    /*//Images
        //$Email = $_REQUEST['username'];
        $Profile_Picture= $_REQUEST['profile_picture'];
        $Image_1 = $_REQUEST['image_1'];
        $Image_2 = $_REQUEST['image_2'];
        $Image_3 = $_REQUEST['image_3'];
        $Image_4 = $_REQUEST['image_4'];
*/
        
    
    //Interest
        //$Email = $_REQUEST['username'];
        /*$Interest_1 = $_REQUEST['interest_1'];
        $Interest_2 = $_REQUEST['interest_2'];
        $Interest_3 = $_REQUEST['interest_3'];
        $Interest_4 = $_REQUEST['interest_4'];
        $Interest_5 = $_REQUEST['interest_5'];
*/
        
    //Descriptiion
        //$Email = $_REQUEST['username'];
        //$Bio = $_REQUEST['biography'];

    //global Vars

        $result="success";

        $Password = password_hash($Password, PASSWORD_DEFAULT);

    //User_Account
        $sql = "INSERT INTO User_Accounts (E_mail, Password, Full_Name, Birthday, Gender, Sexuality) VALUES ('$Email', '$Password', '$Full_Name', '$Birthday', '$Gender', '$Sexuality')";
        
        if ( mysqli_query($conn, $sql) ) {
            if($result!=="error"){
               $result = "success";
            }
            
        } 
        else {
            $result = "error";  
        }

    //Location
       $sql = "INSERT INTO Location (E_mail, Location) VALUES ('$Email', '$Location')";

        if ( mysqli_query($conn, $sql) ) {
            if($result!="success"){
               $result = "success";
            }
            
        } 
        else {
            $result = "error";  
        }
    
    //Description
        /*$sql = "INSERT INTO Description (E_mail, Bio) VALUES ('$Email', '$Bio')";

        if ( mysqli_query($conn, $sql) ) {
            if($result=="success"){
               $result = "success";
            }
            
        } 
        else {
            $result = "error";  
        }*/

        

    //Interest
        /* $sql = "INSERT INTO Interest (E_mail, Interest_1, Interest_2, Interest_3, Interest_4, Interest_5) VALUES ('$Email', '$Interest_1', '$Interest_2', '$Interest_3','$Interest_4', '$Interest_5')";
        
        if ( mysqli_query($conn, $sql) ) {
            if($result=="success"){
               $result = "success";
            }
            
        } 
        else {
            $result = "error";  
        }*/
       

    /*//Images
        $sql = "INSERT INTO Images (E_mail, Profile_Picture, Image_1, Image_2, Image_3, Image_4) VALUES ('$Email', '$Profile_Picture', '$Image_1', '$Image_2', '$Image_3', '$Image_4')";
        
        if ( mysqli_query($conn, $sql) ) {
            if($result=="success"){
               $result = "success";
            }
            
        } 
        else {
            $result = "error";  
        }*/
    
    
    //Account_Link
        /*$sql = "INSERT INTO Location (E_mail, Instagram_Link, Facebook_Link, Twitter_Link) VALUES ('$Email', '$Instagram_Link', '$Facebook_Link', '$Twitter_Link')";

        if ( mysqli_query($conn, $sql) ) {
            if($result=="success"){
               $result = "success";
            }
            
        } 
        else {
            $result = "error";  
        }*/
      

        
    echo json_encode($result);
        
      

?>