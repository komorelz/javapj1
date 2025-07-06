package com.tedu.model.vo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class Audio {
	
	public static final String ADD="music/put.wav";//坦克诞生音效

	public static final String BGM="music/bgm_1.wav";//bgm音效
	public static final String BOOM="music/boom.wav";//爆炸音效
	public static final String OVER="music/gameover.wav";//结束音效



	public static List<AudioClip> getAudios(){
		List<AudioClip> audios = new ArrayList<>();
		try {
			audios.add(Applet.newAudioClip( ( ( new File(Audio.BGM) ).toURI() ).toURL() ) );
			audios.add(Applet.newAudioClip( ( ( new File(Audio.ADD) ).toURI() ).toURL() ) );
			audios.add(Applet.newAudioClip( ( ( new File(Audio.BOOM) ).toURI() ).toURL() ) );
			audios.add(Applet.newAudioClip( ( ( new File(Audio.OVER) ).toURI() ).toURL() ) );

			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return audios;
	}

}
