package lab14;
import lab14lib.Generator;
import lab14lib.GeneratorPlayer;
import lab14lib.GeneratorAudioVisualizer;

public class Main {
	public static void main(String[] args) {
		Generator generator = new AcceleratingSawToothGenerator(200, 0.95);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
		gav.drawAndPlay(1000, 1000);
	}
} 