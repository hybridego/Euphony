package euphony.lib.transmitter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import euphony.lib.util.EuCodec;

public class EuDataEncoder extends EuCodec {
	private String mOriginalSource;
	
	public EuDataEncoder(String _source)
	{
		mOriginalSource = _source;
	}
	
	public String encodeHexCharSource()
	{
		return encodeStaticHexCharSource(mOriginalSource);
	}
	
	public String encodeStaticHexCharSource(String _source)
	{
		String retValue = "";
		for(int i = 0; i < _source.length(); i++)
		{
			int data = _source.charAt(i);
			retValue += Integer.toHexString(data);
		}
		
		return retValue;		
	}
	
	public static byte[] base40StaticEncode(String _source)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			for(int i = 0; i < _source.length(); i+=3)
			{
				switch(Math.min(3, _source.length() - i)) {
				case 1:
					byte b = base40Index[_source.charAt(i)];
					dos.writeByte(b);
					break;
				case 2:
					char ch = (char) ((base40Index[_source.charAt(i+1)]) * 40 + base40Index[_source.charAt(i)]);
					dos.writeChar(ch);
					break;
				case 3:
					char ch2 = (char) ((char) ((base40Index[_source.charAt(i+2)]) * 40 + base40Index[_source.charAt(i+1)]) * 40 + base40Index[_source.charAt(i)]);
					dos.writeChar(ch2);
					
					break;
				}
			}
			return baos.toByteArray();
		} 
		catch(IOException e) 
		{
			throw new AssertionError(e);
		}
	}

	public String getOriginalSource() {
		return mOriginalSource;
	}
	public void setOriginalSource(String mOriginalSource) {
		this.mOriginalSource = mOriginalSource;
	}
}
