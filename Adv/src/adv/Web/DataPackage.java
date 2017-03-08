package adv.Web;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
//TODO make a child class for password
/** 
 * This class use to save data from file, text and image, and carry them to a new device through internet.
 * @author Mike Cai
 * @version v1.00
 */
public class DataPackage implements Serializable {

	private static final long serialVersionUID = 4462539565L;
	private transient boolean isBuild;
	private transient Cipher cipherEncrypt;
	private transient Cipher cipherDecrypt;
	
	boolean isHard=false;
	String commend="";//divide commend by $ and /
	String description="";//information for user to read, divide by $
	String encryption="";//data for know is the file be correct decrypte
	ArrayList<byte[]> data;//encrypted data
	public DataPackage() {
		data=new ArrayList<byte[]>();
		isBuild=false;		    
	}

	private boolean checkPackage(String signature){
		String encryption;
		try {
			byte[] signatureBuff=signature.getBytes();
			Base64.Encoder encoder=Base64.getEncoder();
			Base64.Decoder decoder=Base64.getDecoder();
			byte[] cipherData = cipherEncrypt.doFinal(signatureBuff);
			encryption=encoder.encodeToString(cipherData);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  
		if(encryption.equals(this.encryption))return true;
		return false;
	}
 	private boolean signPackage(String signature){
		
		try {
			byte[] signatureBuff=signature.getBytes();
			Base64.Encoder encoder=Base64.getEncoder();
			Base64.Decoder decoder=Base64.getDecoder();
			byte[] cipherData = cipherEncrypt.doFinal(signatureBuff);
			encryption=encoder.encodeToString(cipherData);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}  
		return true;
	}
	private boolean buildCipher(String id){
		if(id.getBytes().length<8)return false;
		SecretKey secretKey;
		try {
			DESKeySpec keySpec = new DESKeySpec(id.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
			secretKey = keyFactory.generateSecret(keySpec);
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		SecureRandom random = new SecureRandom();  
		try {
			cipherEncrypt = Cipher.getInstance("des");
			cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey, random);
			cipherDecrypt = Cipher.getInstance("des");
			cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, random);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		isBuild=true;
		return true;
	}
	/******************************/
	/**
	 * This method can get the commend for the package.
	 * @return A list of commend.
	 */
	public ArrayList<String[]> getCommend(){
		String[] outputBuff= commend.split("$");
		ArrayList<String[]> output=new ArrayList<String[]>();
		for(int i=0;i<outputBuff.length;i++){
			output.add(outputBuff[i].split("/"));
		}
		return output;
	}
	/**
	 * This method can get the description for the package.
	 * @return A list of description.
	 */
	public ArrayList<String> getDescription(){
		String[] outputBuff= description.split("$");
		ArrayList<String> output=new ArrayList<String>();
		for(int i=0;i<outputBuff.length;i++){
			output.add(outputBuff[i]);
		}
		return output;
	}
	/**
	 * This method can get the data for the package.
	 * @param  pointer-which data in data list user want to get.
	 * @return the data in position that pointer point to.
	 */
	public byte[] getData(int pointer){
		try {
			byte[] dataBuff=data.get(pointer);
			byte[] buff=cipherDecrypt.doFinal(dataBuff);
			return buff;
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * This method can check the signature and id is valid for this package.
	 * @param signature-for check the package can or can not be decrypte, id- password for decrypte
	 * @return A boolean that show the signature and id is valid or not.
	 */
	public boolean checkSign(String signature,String id){
		if(buildCipher(id)&&checkPackage(signature))return true;
		else {
			cipherEncrypt=null;
			cipherDecrypt=null;
			if(isHard){
				commend="";
				description="";
				encryption="";
				data=null;
			}
			return false;
		}
	}
	/******************************/
	/**
	 * This method can init a package and wait for input data.
	 * @param signature-for create a text that will be use later for check this package, id- password for decrypte
	 * @return is the signature and id valid
	 */
	public boolean init(String signature,String id){
		if(buildCipher(id)&&signPackage(signature))return true;
		else return false;
	}
	/**
	 * This method can add a commend in the package.
	 * @param commend-confirm what the data used to, pointer-which data in data list user want to get.
	 */
	public void addCommend(String commend,int pointer){
		this.commend+=commend+"/"+pointer+"$";
	}
	/**
	 * This method can add a description in the package.
	 * @param description-for descrip data.
	 */
	public void addDescription(String description){
		this.description+=description+"$";
	}
	/**
	 * This method can add a data in the package.
	 * @param 
	 * @return A boolean that show the data is valid or not.
	 */
	public boolean addData(String dataType,byte[] data){
		if(isBuild){
			try {
				addCommend("DataType:"+dataType,this.data.size());
				byte[] buff = cipherEncrypt.doFinal(data);
				this.data.add(buff);
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
DatagramPacket	public DataPackage output(){
		return this;
	}
}
