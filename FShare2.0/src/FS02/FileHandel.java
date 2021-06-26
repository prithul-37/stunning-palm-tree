package FS02;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

public class FileHandel {
    // Function to send file
    public static void sendFile(Socket socket, File file) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = socket;
        try {
            // send file
            File myFile = file;
            byte [] mybytearray  = new byte [(int)myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            sendController.xx = "Preparing File";
            bis.read(mybytearray,0,mybytearray.length);
            sendController.xx  = "File Sending..!";
            os = sock.getOutputStream();
            DataOutputStream DOS = new DataOutputStream(os);
            System.out.println("Sending " + myFile.getName() + "(" + mybytearray.length + " bytes)");
            DOS.write(mybytearray,0,mybytearray.length);
            DOS.flush();
            sendController.xx = "File Sent..!";
            System.out.println("Done.");
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

     //Function to receive file

    public static void receiveFile(Socket socket,String fileName,long fileSize) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        long fullFileSize = fileSize;
        float progress = 0;
        try {
            sock = socket;

            // receive file
            byte [] mybytearray  = new byte [1024*1024];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            DataInputStream DS = new DataInputStream(is);
            while (fileSize > 0 && (bytesRead = DS.read(mybytearray, 0, (int)Math.min(mybytearray.length, fileSize))) != -1)
            {
                bos.write(mybytearray, 0, bytesRead);
                progress = (float)(1.00-(float)fileSize/(float) fullFileSize);
                receiveController.x1 = progress;
                receiveController.xx = String.valueOf((int)(progress*100))+"%";
                fileSize -= bytesRead;
            }
            receiveController.xx = "100%";
            bos.flush();
            System.out.println("Done");
            //receiveController.status.setText("File sent..!");
            // DS.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

