package com.tedu.model.vo;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class audioPlay {

	private String fileName = null;
	private AudioInputStream cin = null;
	private AudioFormat format = null;
	private DataLine.Info info = null;
	private SourceDataLine line = null;
	
	private boolean stop = false;
	
	public audioPlay(String fileName){
		this.fileName = fileName;
	}
	
	@SuppressWarnings("deprecation")
	public void player(){
		try {
			cin = AudioSystem.getAudioInputStream(new File(fileName).toURI().toURL());
			format = cin.getFormat();
			info = new DataLine.Info(SourceDataLine.class, format);
			line = (SourceDataLine)AudioSystem.getLine(info);
			
			line.open(format);
			line.start();
			
			int len = 0;
			byte[] buffer = new byte[512];
			
			while(!stop){
				len = cin.read(buffer, 0 , buffer.length);
				if(len <= 0){
					break;
				}
				line.write(buffer, 0 , len);
			}
			line.drain();
			line.close();
		}catch (IOException e) {
			e.printStackTrace();
		} 
		catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} 
	}
	
//	public void player2(String Filename) {
//		InputStream in = new FileInputStream(Filename);//FIlename 是你加载的声音文件如("game.mav")　//获得音频文件
//		AudioStream as = new AudioStream(in);
//
//		AudioData data = as.getData();
//
//		// Create ContinuousAudioDataStream.
//		ContinuousAudioDataStream gg = new ContinuousAudioDataStream (data);
//
//		AudioPlayer.player.start (gg);　 // 播放
//		AudioPlayer.player.stop (gg); // 停止
//	}

	public void stop(){
		if(line != null){
			line.stop();
			stop = true;
		}
	}

	 //内部类实现播放音乐线程
	 

}
