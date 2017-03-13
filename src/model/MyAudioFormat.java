package model;

import java.io.Serializable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

public class MyAudioFormat  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4137772403844880469L;
	
	private float sampleRate;
	private int sampleSizeInBits;
	private int channels;
	private int frameSize;
	private float frameRate;
	private boolean bigEndian;
	private String encoding;
	
	
	public MyAudioFormat() {}
	
	
	public MyAudioFormat(Encoding encoding, float sampleRate, int sampleSizeInBits, int channels, int frameSize,
			float frameRate, boolean bigEndian) {
		
		
	}
	
	
	public void set(AudioFormat af) {
		this.encoding = af.getEncoding().toString();
		this.sampleRate = af.getSampleRate();
		this.sampleSizeInBits = af.getSampleSizeInBits();
		this.channels = af.getChannels();
		this.frameSize = af.getFrameSize();
		this.frameRate = af.getFrameRate();
		this.bigEndian = af.isBigEndian();
	}


	public float getSampleRate() {
		return sampleRate;
	}


	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}


	public int getSampleSizeInBits() {
		return sampleSizeInBits;
	}


	public void setSampleSizeInBits(int sampleSizeInBits) {
		this.sampleSizeInBits = sampleSizeInBits;
	}


	public int getChannels() {
		return channels;
	}


	public void setChannels(int channels) {
		this.channels = channels;
	}


	public int getFrameSize() {
		return frameSize;
	}


	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
	}


	public float getFrameRate() {
		return frameRate;
	}


	public void setFrameRate(float frameRate) {
		this.frameRate = frameRate;
	}


	public boolean isBigEndian() {
		return bigEndian;
	}


	public void setBigEndian(boolean bigEndian) {
		this.bigEndian = bigEndian;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	

}
