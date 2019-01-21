jars="./MLResearch.jar:./lib/weka-dev-3.9.3.jar:./AutoWEKA-2.6.jar:./lib/arpack_combined_all-0.1.jar:./lib/bounce-0.18.jar:./lib/core-1.1.jar:./lib/gson-2.8.5.jar:./lib/java-cup-11b-2015.03.26.jar:./lib/java-cup-11b-runtime-2015.03.26.jar:./lib/jniloader-1.1.jar:./lib/mtj-1.0.4.jar:./lib/native_ref-java-1.1.jar:./lib/native_system-java-1.1.jar:./lib/netlib-java-1.1.jar:./lib/netlib-native_ref-linux-armhf-1.1-natives.jar:./lib/netlib-native_ref-linux-i686-1.1-natives.jar:./lib/netlib-native_ref-linux-x86_64-1.1-natives.jar:./lib/netlib-native_ref-osx-x86_64-1.1-natives.jar:./lib/netlib-native_ref-win-i686-1.1-natives.jar:./lib/netlib-native_ref-win-x86_64-1.1-natives.jar:./lib/netlib-native_system-linux-armhf-1.1-natives.jar:./lib/netlib-native_system-linux-i686-1.1-natives.jar:./lib/netlib-native_system-linux-x86_64-1.1-natives.jar:./lib/netlib-native_system-osx-x86_64-1.1-natives.jar:./lib/netlib-native_system-win-i686-1.1-natives.jar:./lib/netlib-native_system-win-x86_64-1.1-natives.jar"
args="-Xms512m -Xmx4096m -Xss256m -Xmn512m"
app="br.ufrn.imd.experiments.ExperimenterBase"
java $args -cp ".:$jars" $app "Glass Identification.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
#java $args -cp ".:$jars" $app "Flags.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
#java $args -cp ".:$jars" $app "Car.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
#java $args -cp ".:$jars" $app "GermanCredit.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
#java $args -cp ".:$jars" $app "Wine.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
#java $args -cp ".:$jars" $app "Semeion.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
#java $args -cp ".:$jars" $app "Adult.arff" Auto_WEKA 123 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Glass Identification.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Flags.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Car.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "GermanCredit.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Wine.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Semeion.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Adult.arff" Auto_WEKA 250 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Glass Identification.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Flags.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Car.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "GermanCredit.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Wine.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Semeion.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Adult.arff" Auto_WEKA 377 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Glass Identification.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Flags.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Car.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "GermanCredit.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Wine.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Semeion.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Adult.arff" Auto_WEKA 504 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Glass Identification.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Flags.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Car.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "GermanCredit.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Wine.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Semeion.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
java $args -cp ".:$jars" $app "Adult.arff" Auto_WEKA 631 ResAuto_WEKA0.txt
sleep(5d)
