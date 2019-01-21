import java.io.IOException;
import java.io.PrintStream;

public class Test {

	public static void main(String[] args) throws IOException {
		String[][] bases = {
				{ "Glass Identification.arff", "Flags.arff", "Car.arff", "GermanCredit.arff", "Wine.arff", "Semeion.arff", "Adult.arff" },
				{ "SolarFlare1.arff", "Automobile.arff", "Yeast.arff", "Abalone.arff", "Image Segmentation.arff", "Waveform.arff", "Madelon.arff" },
				{ "Ecoli.arff", "Dermatology.arff", "Sonar.arff", "KR-vs-KP.arff", "Arrhythmia.arff", "Nursery.arff", "Secom.arff" }
		};

		int numThreads = 3;
		int[] seeds = new int[5];
		for (int i = 0; i < seeds.length; i++) {
			seeds[i] = i * 127 + 123;
		}

		String[] algs = { "Auto_WEKA", "PBIL_Auto_Ens_v1", "PBIL_Auto_Ens_v2" };

		for (String alg : algs) {
			for (int thread = 0; thread < numThreads; thread++) {
				PrintStream out = new PrintStream("Exp" + alg + thread + ".sh");
				out.println(
						"jars=\"./MLResearch.jar:./lib/weka-dev-3.9.3.jar:./AutoWEKA-2.6.jar:./lib/arpack_combined_all-0.1.jar:./lib/bounce-0.18.jar:./lib/core-1.1.jar:./lib/gson-2.8.5.jar:./lib/java-cup-11b-2015.03.26.jar:./lib/java-cup-11b-runtime-2015.03.26.jar:./lib/jniloader-1.1.jar:./lib/mtj-1.0.4.jar:./lib/native_ref-java-1.1.jar:./lib/native_system-java-1.1.jar:./lib/netlib-java-1.1.jar:./lib/netlib-native_ref-linux-armhf-1.1-natives.jar:./lib/netlib-native_ref-linux-i686-1.1-natives.jar:./lib/netlib-native_ref-linux-x86_64-1.1-natives.jar:./lib/netlib-native_ref-osx-x86_64-1.1-natives.jar:./lib/netlib-native_ref-win-i686-1.1-natives.jar:./lib/netlib-native_ref-win-x86_64-1.1-natives.jar:./lib/netlib-native_system-linux-armhf-1.1-natives.jar:./lib/netlib-native_system-linux-i686-1.1-natives.jar:./lib/netlib-native_system-linux-x86_64-1.1-natives.jar:./lib/netlib-native_system-osx-x86_64-1.1-natives.jar:./lib/netlib-native_system-win-i686-1.1-natives.jar:./lib/netlib-native_system-win-x86_64-1.1-natives.jar\"");
				out.println("args=\"-Xms512m -Xmx4096m -Xss256m -Xmn512m\"");
				out.println("app=\"br.ufrn.imd.experiments.ExperimenterBase\"");
				for (int seed : seeds) {
					for (String base : bases[thread]) {
						out.println(String.format("java $args -cp \".:$jars\" $app \"%s\" %s %s %s", base, alg, seed, "Res" + alg + thread + ".txt"));

					}
				}
				out.println("sleep(5d)");
				out.close();
			}
		}
	}
}
