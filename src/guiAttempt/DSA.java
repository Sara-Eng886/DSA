package guiAttempt;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FileDialog;
import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class DSA {
	private JFrame frmDigitalSignature;
	private JTextField textField;
	private JTextField txtType;
	private JTextField txtSize;
	private JTextField txtName;
	private JTextField txtLocation;
	private JTextField txtDateOfCreation;
	private JTextField txttypeoffile;
	private JTextField txtsizeoffile;
	private JTextField txtnameoffile;
	private JTextField txtlocationoffile;
	private JTextField Dateofcreationoffile;
	private static  JTextField publickeygeneratetxt;
	private static JTextField privatekeygeneratetxt;
	private JTextField txtPublicKey;
	private JTextField txtPrivateKey;
	public static int p,q;
	public static Integer ep,d;
	public static int n;
	public static int phi_n;
	static BigInteger cipher;
	static BigInteger encode;
	static int MessageNumber ;
	private JTextField txtnkey;
	private JTextField PrintNkey;
	private JTextField textofhashingfunction;
	private JTextField TxtHash;
	private JTextField TxtEncryption;
	private JTextField TxtDecyrption;
	private JTextField TEXTEncryptfile;
	private JTextField decryptionoffile;
	private JTextField txtHashofencryption;
	private JTextField Hashofencryptionfile;

//-----------------------------random generate number function--------------------------------------------------------------------
static void generaterandomprime() 
{
	Random random = new Random();
	boolean isprimep;
	boolean isprimeq;
    
	do 
	{
		p = random.nextInt(65535);
		isprimep = true;
		for (int i = 2; i < p / 2; i++)
		{
			if (p % i == 0) 
			{
				isprimep = false;
				break;
			}
		}
		
		q = random.nextInt(65535);
		isprimeq = true;
		for (int i = 2; i < q / 2; i++) 
		{
			if (q % i == 0) 
			{
				isprimeq = false;
				break;
			}
	
		}
	}
	while (!isprimeq && !isprimep && p==q);
	
}
//-------------------------------------finding n,phi_n,p,q,e,d---------------------------------------------------------------
public static void generate()
{
	do
	{
	Random random = new Random();
	
	n = p * q;
	phi_n = (p - 1) * (q - 1);
	
	ep = random.nextInt(phi_n + 1);
	//d=(e.modInverse(phi_n));
	d= getModInverse(ep,phi_n);
	}
	while(d==1);
	
	
}
//-----------------------getting mode inverses to find d-------------------------------------------------------------------
static int getModInverse(int a, int m)
    {
      
        for (int x = 1; x < m; x++)
            if (((a%m) * (x%m)) % m == 1)
                return x;
       return 1;
      
    }
public static String convertStringToBinary(String array) 
{

    StringBuilder result = new StringBuilder();
    char[] chars = array.toCharArray();
    for (char aChar : chars) {
        result.append(
                String.format("%8s", Integer.toBinaryString(aChar))   
                        .replaceAll(" ", "0")                         
        );
    }
    return result.toString();
}
public static String concatenate(String... s)
{
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length; i++)
    {
        sb = sb.append(s[i]);
    }

    return sb.toString();
}


//---------------------------------creating hash function-------------------------------------------------------------------
public static String encryptThisString(String input)
{
    try 
    {
        MessageDigest fileDialog = MessageDigest.getInstance("SHA-512");
        byte[] messageDigest = fileDialog.digest(input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 64) 
        {
            hashtext = "0" + hashtext;
            
        }
        return hashtext;
    }
    catch (NoSuchAlgorithmException e)
    {
        throw new RuntimeException(e);
    }
}
//------------------Main ----------------------------------------------------------------------------------------------
	public static void main(String args[]) throws NoSuchAlgorithmException 
	{
	
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					DSA window = new DSA();
					window.frmDigitalSignature.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DSA() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDigitalSignature = new JFrame();
		frmDigitalSignature.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmDigitalSignature.getContentPane().setForeground(Color.WHITE);
		frmDigitalSignature.getContentPane().setFont(UIManager.getFont("CheckBoxMenuItem.acceleratorFont"));
		frmDigitalSignature.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
		frmDigitalSignature.setBackground(Color.DARK_GRAY);
		frmDigitalSignature.setTitle("Digital Signature");
		frmDigitalSignature.setBounds(100, 100, 890, 426);
		frmDigitalSignature.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDigitalSignature.getContentPane().setLayout(null);
		JButton BTNFORBROWSE = new JButton("Browse");
		BTNFORBROWSE.setForeground(Color.BLACK);
		BTNFORBROWSE.setBackground(Color.LIGHT_GRAY);
		
		
		
		
		
		
		
//---------------opening file--------------------------------------------------------------------------
		final FileDialog fileDialog = new FileDialog(frmDigitalSignature,"Select file");
		fileDialog.setBackground(Color.DARK_GRAY);
		BTNFORBROWSE.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				fileDialog.setVisible(true);
				System.out.println("File Selected :" + fileDialog.getDirectory() + fileDialog.getFile());
				try 
				{
					FileInputStream fis=new FileInputStream(fileDialog.getDirectory() + fileDialog.getFile());
					int c;
					while ((c=fis.read())!=-1) 
					{
						System.out.print((char)c);
					}
					
//--------------------------printing out the details of the selected file-------------------------------------------------------
					textField.setText(fileDialog.getFile());
					String fileName=String.valueOf(fileDialog.getFile().replaceFirst("[.][^.]+$", ""));
					txtnameoffile.setText(fileName);
					txttypeoffile.setText(fileDialog.getFile().substring(fileDialog.getFile().lastIndexOf('.'),fileDialog.getFile().length()));
					txtlocationoffile.setText(fileDialog.getDirectory());
					String absPath=fileDialog.getFile();
					File  file=new File(absPath);
				    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);					
				    txtsizeoffile.setText(file.length()+" bytes");
				    Dateofcreationoffile.setText(attr.creationTime().toString());
					String s1 = fileDialog.getFile();
					 String s2=encryptThisString(s1);
					 textofhashingfunction.setText(s2);
					 
				
					 
generaterandomprime();
generate();

//--------------------------------saving msg in array of index of 7 chnage to binary joing the array-------------------------------
 String message= s2;
 String[] array=message.split("(?<=\\G.{7})");
 String z=java.util.Arrays.toString(array);
 int i;
String[] newarray = new String[array.length];
      for ( i=0; i<=array.length-1; i++) 
      {
           for (String str : array) 
           {
             newarray[i]= convertStringToBinary(array[i]);
      }
           }
							
String joinedstring = Stream.of(newarray).map(arr -> new String(arr)).collect(Collectors.joining());
BigInteger MessageNumber = new BigInteger(joinedstring, 2);
BigInteger dBig = BigInteger.valueOf(d);
BigInteger nBig = BigInteger.valueOf(n);
BigInteger eBig = BigInteger.valueOf(ep);
					
	 publickeygeneratetxt.setText(String.valueOf(eBig));
	 privatekeygeneratetxt.setText(String.valueOf(dBig));
	 PrintNkey.setText(String.valueOf(nBig));
	 
//---------------------------saving keys to a file called Keys.txt----------------------------------------------------------------
 File f2 = new File("keys.txt");
  FileOutputStream fos2 = new FileOutputStream(f2);
 PrintWriter pw2 = new PrintWriter(fos2);
 pw2.write(String.valueOf(eBig)+"keyE \n");
 pw2.write(String.valueOf(nBig)+"keyN \n");
 pw2.write(String.valueOf(dBig)+"keyD \n");
 pw2.flush();
 fos2.close();
 pw2.close();
cipher = MessageNumber.modPow(dBig, nBig);
TEXTEncryptfile.setText(String.valueOf(cipher));
//------------------------------------------decrypting--------------------------------------------------------------------				
					


//-----------------------------geting the hash of encrypted fata----------------------------------------------------------
String s3=encryptThisString(String.valueOf(cipher));

					Hashofencryptionfile.setText(s3);
					//print the hashed cipher
					System.out.println(s3);
					String message2= s3;
					  String[] array2=message2.split("(?<=\\G.{7})");
					  String z2=java.util.Arrays.toString(array2);
					  System.out.println(z2);

						int i2;
						String[] newarray2 = new String[array2.length];
						for ( i2=0; i2<=array2.length-1; i2++) {

						    for (String str2 : array2) {
						    	 newarray2[i2]= convertStringToBinary(array2[i2]);
						    }

						}
						
						String joinedstring2 = Stream.of(newarray2).map(arr2 -> new String(arr2)).collect(Collectors.joining());
						System.out.println(joinedstring2);
						
						BigInteger MessageNumber2 = new BigInteger(joinedstring, 2);
						
					
						
						 encode = cipher.modPow(eBig, nBig);
						 
						 System.out.println(encode);
						 
							String decrypted=String.valueOf(encode);
							decryptionoffile.setText(decrypted);
					  //saving the hashed password
						File f = new File("Output.txt");
						
					  FileOutputStream fos = new FileOutputStream(f);
					     PrintWriter pw = new PrintWriter(fos);
					      pw.write(String.valueOf(cipher));
					      pw.write(decrypted);
					      pw.flush();
					      fos.close();
					      pw.close();
					      
					      
					      
					      

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
	
		});
		
		BTNFORBROWSE.setBounds(301, 23, 89, 23);
		frmDigitalSignature.getContentPane().add(BTNFORBROWSE);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setBounds(10, 24, 272, 20);
		frmDigitalSignature.getContentPane().add(textField);
		textField.setColumns(10);
		
		txtType = new JTextField();
		txtType.setBackground(Color.WHITE);
		txtType.setForeground(Color.BLACK);
		txtType.setEnabled(false);
		txtType.setEditable(false);
		txtType.setText("Type");
		txtType.setBounds(10, 55, 96, 20);
		frmDigitalSignature.getContentPane().add(txtType);
		txtType.setColumns(10);
		
		txtSize = new JTextField();
		txtSize.setBackground(Color.WHITE);
		txtSize.setEditable(false);
		txtSize.setEnabled(false);
		txtSize.setText("Size");
		txtSize.setHorizontalAlignment(SwingConstants.LEFT);
		txtSize.setColumns(10);
		txtSize.setBounds(10, 86, 96, 20);
		frmDigitalSignature.getContentPane().add(txtSize);
		
		txtName = new JTextField();
		txtName.setBackground(Color.WHITE);
		txtName.setEditable(false);
		txtName.setEnabled(false);
		txtName.setText("Name");
		txtName.setColumns(10);
		txtName.setBounds(10, 117, 96, 20);
		frmDigitalSignature.getContentPane().add(txtName);
		
		txtLocation = new JTextField();
		txtLocation.setBackground(Color.WHITE);
		txtLocation.setEnabled(false);
		txtLocation.setEditable(false);
		txtLocation.setText("Location");
		txtLocation.setColumns(10);
		txtLocation.setBounds(10, 148, 96, 20);
		frmDigitalSignature.getContentPane().add(txtLocation);
		
		txtDateOfCreation = new JTextField();
		txtDateOfCreation.setBackground(Color.WHITE);
		txtDateOfCreation.setEditable(false);
		txtDateOfCreation.setEnabled(false);
		txtDateOfCreation.setText("Date Of creation");
		txtDateOfCreation.setColumns(10);
		txtDateOfCreation.setBounds(10, 179, 96, 20);
		frmDigitalSignature.getContentPane().add(txtDateOfCreation);
		
		txttypeoffile = new JTextField();
		txttypeoffile.setBackground(Color.WHITE);
		txttypeoffile.setEnabled(false);
		txttypeoffile.setColumns(10);
		txttypeoffile.setBounds(120, 55, 189, 20);
		frmDigitalSignature.getContentPane().add(txttypeoffile);
		
		txtsizeoffile = new JTextField();
		txtsizeoffile.setBackground(Color.WHITE);
		txtsizeoffile.setEnabled(false);
		txtsizeoffile.setColumns(10);
		txtsizeoffile.setBounds(120, 86, 189, 20);
		frmDigitalSignature.getContentPane().add(txtsizeoffile);
		
		txtnameoffile = new JTextField();
		txtnameoffile.setBackground(Color.WHITE);
		txtnameoffile.setEnabled(false);
		txtnameoffile.setColumns(10);
		txtnameoffile.setBounds(120, 117, 189, 20);
		frmDigitalSignature.getContentPane().add(txtnameoffile);
		
		txtlocationoffile = new JTextField();
		txtlocationoffile.setBackground(Color.WHITE);
		txtlocationoffile.setEnabled(false);
		txtlocationoffile.setColumns(10);
		txtlocationoffile.setBounds(120, 148, 189, 20);
		frmDigitalSignature.getContentPane().add(txtlocationoffile);
		
		Dateofcreationoffile = new JTextField();
		Dateofcreationoffile.setBackground(Color.WHITE);
		Dateofcreationoffile.setColumns(10);
		Dateofcreationoffile.setEnabled(false);
		Dateofcreationoffile.setBounds(120, 179, 189, 20);
		frmDigitalSignature.getContentPane().add(Dateofcreationoffile);
		
		publickeygeneratetxt = new JTextField();
		publickeygeneratetxt.setBackground(Color.WHITE);
		publickeygeneratetxt.setColumns(10);
		publickeygeneratetxt.setBounds(301, 242, 247, 23);
		frmDigitalSignature.getContentPane().add(publickeygeneratetxt);
		
		privatekeygeneratetxt = new JTextField();
		privatekeygeneratetxt.setBackground(Color.WHITE);
		privatekeygeneratetxt.setColumns(10);
		privatekeygeneratetxt.setBounds(593, 242, 242, 23);
		frmDigitalSignature.getContentPane().add(privatekeygeneratetxt);
		
		txtPublicKey = new JTextField();
		txtPublicKey.setBackground(Color.LIGHT_GRAY);
		txtPublicKey.setEditable(false);
		txtPublicKey.setEnabled(false);
		txtPublicKey.setText("Public key");
		txtPublicKey.setBounds(301, 226, 247, 20);
		frmDigitalSignature.getContentPane().add(txtPublicKey);
		txtPublicKey.setColumns(10);
		
		txtPrivateKey = new JTextField();
		txtPrivateKey.setBackground(Color.LIGHT_GRAY);
		txtPrivateKey.setText("Private key");
		txtPrivateKey.setEnabled(false);
		txtPrivateKey.setEditable(false);
		txtPrivateKey.setColumns(10);
		txtPrivateKey.setBounds(593, 228, 242, 20);
		frmDigitalSignature.getContentPane().add(txtPrivateKey);
		
		txtnkey = new JTextField();
		txtnkey.setForeground(Color.BLACK);
		txtnkey.setBackground(Color.LIGHT_GRAY);
		txtnkey.setText("N key");
		txtnkey.setEnabled(false);
		txtnkey.setEditable(false);
		txtnkey.setColumns(10);
		txtnkey.setBounds(10, 226, 242, 20);
		frmDigitalSignature.getContentPane().add(txtnkey);
		
		PrintNkey = new JTextField();
		PrintNkey.setBackground(Color.WHITE);
		PrintNkey.setColumns(10);
		PrintNkey.setBounds(10, 242, 242, 23);
		frmDigitalSignature.getContentPane().add(PrintNkey);
		
		textofhashingfunction = new JTextField();
		textofhashingfunction.setBackground(Color.WHITE);
		textofhashingfunction.setColumns(10);
		textofhashingfunction.setBounds(506, 24, 360, 20);
		frmDigitalSignature.getContentPane().add(textofhashingfunction);
		
		TxtHash = new JTextField();
		TxtHash.setBackground(Color.WHITE);
		TxtHash.setText("Hash");
		TxtHash.setForeground(Color.BLACK);
		TxtHash.setEnabled(false);
		TxtHash.setEditable(false);
		TxtHash.setColumns(10);
		TxtHash.setBounds(400, 24, 96, 20);
		frmDigitalSignature.getContentPane().add(TxtHash);
		
		TxtEncryption = new JTextField();
		TxtEncryption.setBackground(Color.WHITE);
		TxtEncryption.setText("Encrypt");
		TxtEncryption.setForeground(Color.BLACK);
		TxtEncryption.setEnabled(false);
		TxtEncryption.setEditable(false);
		TxtEncryption.setColumns(10);
		TxtEncryption.setBounds(400, 55, 96, 20);
		frmDigitalSignature.getContentPane().add(TxtEncryption);
		
		TxtDecyrption = new JTextField();
		TxtDecyrption.setBackground(Color.WHITE);
		TxtDecyrption.setText("Decrypt");
		TxtDecyrption.setForeground(Color.BLACK);
		TxtDecyrption.setEnabled(false);
		TxtDecyrption.setEditable(false);
		TxtDecyrption.setColumns(10);
		TxtDecyrption.setBounds(400, 117, 96, 20);
		frmDigitalSignature.getContentPane().add(TxtDecyrption);
		
		TEXTEncryptfile = new JTextField();
		TEXTEncryptfile.setColumns(10);
		TEXTEncryptfile.setBackground(Color.WHITE);
		TEXTEncryptfile.setBounds(506, 55, 360, 20);
		frmDigitalSignature.getContentPane().add(TEXTEncryptfile);
		
		decryptionoffile = new JTextField();
		decryptionoffile.setColumns(10);
		decryptionoffile.setBackground(Color.WHITE);
		decryptionoffile.setBounds(506, 117, 360, 20);
		frmDigitalSignature.getContentPane().add(decryptionoffile);
		
		txtHashofencryption = new JTextField();
		txtHashofencryption.setText("HashOfEncryption");
		txtHashofencryption.setForeground(Color.BLACK);
		txtHashofencryption.setEnabled(false);
		txtHashofencryption.setEditable(false);
		txtHashofencryption.setColumns(10);
		txtHashofencryption.setBackground(Color.WHITE);
		txtHashofencryption.setBounds(400, 86, 96, 20);
		frmDigitalSignature.getContentPane().add(txtHashofencryption);
		
		Hashofencryptionfile = new JTextField();
		Hashofencryptionfile.setColumns(10);
		Hashofencryptionfile.setBackground(Color.WHITE);
		Hashofencryptionfile.setBounds(506, 86, 360, 20);
		frmDigitalSignature.getContentPane().add(Hashofencryptionfile);
	}
}
