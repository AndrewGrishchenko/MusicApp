package com.andrew.audio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

public final class Mp3Utils {
	private static FFmpeg ffmpeg;
	private static FFprobe ffprobe;

	public static void init () throws IOException {
		ffmpeg = new FFmpeg();
		ffprobe = new FFprobe();
	}

	public static File convert (File input) {
		Path tmpPath = Paths.get(input.getParent(), "tmp");
		
		if (!Files.exists(tmpPath)) {
			new File(tmpPath.toString()).mkdirs();
		}
		
		String source = input.getPath();
		String destination = Paths.get(tmpPath.toString(), input.getName().replace(".mp3", ".wav")).toString();

		if (Files.exists(Paths.get(destination))) {
			return Paths.get(destination).toFile();
		}

		FFmpegBuilder ffmpegBuilder = new FFmpegBuilder()
		.setInput(source)
		.addOutput(destination)
			.setFormat("wav")
			.setAudioCodec("pcm_u8")
		.done();

		FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
		executor.createJob(ffmpegBuilder).run();

		return Paths.get(destination).toFile();
	}

	public static String getDuration (File file) {
		try {
			FFmpegProbeResult probeResult = ffprobe.probe(file.getPath());
			FFmpegFormat format = probeResult.getFormat();

			int duration = (int) format.duration;
			int sec = duration % 60;
			int min = duration / 60;

			String minutes = String.valueOf(min);
			String seconds = String.valueOf(sec);
			if (seconds.length() == 1) seconds = "0" + seconds;

			return minutes + ":" + seconds;
		} catch (IOException e) {
			return null;
		}
    }
}