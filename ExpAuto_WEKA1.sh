jars="./MLResearch.jar:./lib/weka-dev-3.9.3.jar:./AutoWEKA-2.6.jar:./lib/arpack_combined_all-0.1.jar:./lib/bounce-0.18.jar:./lib/core-1.1.jar:./lib/gson-2.8.5.jar:./lib/java-cup-11b-2015.03.26.jar:./lib/java-cup-11b-runtime-2015.03.26.jar:./lib/jniloader-1.1.jar:./lib/mtj-1.0.4.jar:./lib/native_ref-java-1.1.jar:./lib/native_system-java-1.1.jar:./lib/netlib-java-1.1.jar:./lib/netlib-native_ref-linux-armhf-1.1-natives.jar:./lib/netlib-native_ref-linux-i686-1.1-natives.jar:./lib/netlib-native_ref-linux-x86_64-1.1-natives.jar:./lib/netlib-native_ref-osx-x86_64-1.1-natives.jar:./lib/netlib-native_ref-win-i686-1.1-natives.jar:./lib/netlib-native_ref-win-x86_64-1.1-natives.jar:./lib/netlib-native_system-linux-armhf-1.1-natives.jar:./lib/netlib-native_system-linux-i686-1.1-natives.jar:./lib/netlib-native_system-linux-x86_64-1.1-natives.jar:./lib/netlib-native_system-osx-x86_64-1.1-natives.jar:./lib/netlib-native_system-win-i686-1.1-natives.jar:./lib/netlib-native_system-win-x86_64-1.1-natives.jar"
args="-Xms512m -Xmx4096m -Xss256m -Xmn512m"
app="br.ufrn.imd.experiments.ExperimenterBase"

java $args -cp ".:$jars" $app "Yeast.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Yeast.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Yeast.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Abalone.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Abalone.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Abalone.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Waveform.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Waveform.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Waveform.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Madelon.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Madelon.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Madelon.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Yeast.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Yeast.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Abalone.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Abalone.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Waveform.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Waveform.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Madelon.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Madelon.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt

java $args -cp ".:$jars" $app "SolarFlare1.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "SolarFlare1.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "SolarFlare1.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Automobile.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Automobile.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Automobile.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Image Segmentation.arff" Auto_WEKA 123 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Image Segmentation.arff" Auto_WEKA 250 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Image Segmentation.arff" Auto_WEKA 377 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "SolarFlare1.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "SolarFlare1.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Automobile.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Automobile.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Image Segmentation.arff" Auto_WEKA 504 V2_ResAuto_WEKA1.txt
java $args -cp ".:$jars" $app "Image Segmentation.arff" Auto_WEKA 631 V2_ResAuto_WEKA1.txt
sleep(5d)
