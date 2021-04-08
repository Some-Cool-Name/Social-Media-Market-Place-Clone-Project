<?php
    $conn = mysqli_connect("127.0.0.1", "s1851427", "78714377", "d1851427");
    $Email = $_REQUEST['username'];
    $sql = "SELECT * FROM User_Accounts WHERE E_mail='$Email' ";
    $response = mysqli_query($conn, $sql);
    $result['login'] = array();

    if ( mysqli_num_rows($response) === 1 ) {
        $Password = $_REQUEST['password'];
       //       $hash = password_hash($Password,PASSWORD_DEFAULT);
        $Verify_Password = "SELECT Password FROM User_Accounts like '$Email' ";

        $row = mysqli_fetch_assoc($response);
        //$result[] = $row;
        if ( password_verify($Password,$row['Password'])) {

            $index['username'] = $row['E_mail'];
            //$index['password'] = $row['Password'];
            $index['name'] = $row['Full_Name'];
            $index['Birthday'] = $row['Birthday'];
            $index['gender'] = $row['Gender'];
            $index['Sexuality'] = $row['Sexuality'];



            array_push($result['login'], $index);
            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);
            mysqli_close($conn);
        } else {
            $result['success'] = "0";
            $result['message'] = "Wrong Password";
            echo json_encode($result);
            mysqli_close($conn);
        }
    }else {
            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);
            mysqli_close($conn);
        }

?>