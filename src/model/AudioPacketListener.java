package model;

import java.util.EventListener;

public interface AudioPacketListener extends EventListener  {
	public void audioBufferFilled(AudioPacketEvent ev);
}
