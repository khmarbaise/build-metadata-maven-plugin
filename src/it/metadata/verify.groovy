File file = new File( basedir, "build.log" );
assert file.exists();

def text = file.getText("utf-8");
//
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.runtime\.name = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.runtime\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.specification\.name = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.specification\.vendor = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.specification\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vendor = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.name = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.info = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.vendor = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.specification\.name = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.specification\.vendor = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.java\.vm\.specification\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.sun\.management\.compiler = "(.*)"/

assert text =~ /\[DEBUG\] define property build\.metadata\.java\.opts = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.os\.family = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.os\.arch = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.os\.name = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.os\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.execution\.cmdline = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.execution\.goals = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.username = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.sun\.management\.compiler = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.active\.profiles = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.execution\.opts = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.metadata\.hostname = "(.*)"/

File reportFile = new File(basedir, "target/build-properties.xml");
assert reportFile.exists();

def buildProps = reportFile.getText("utf-8")

assert buildProps =~ /build\.metadata\.java\.runtime\.name=.*/
assert buildProps =~ /build\.metadata\.java\.runtime\.version=.*/
assert buildProps =~ /build\.metadata\.java\.specification\.name=.*/
assert buildProps =~ /build\.metadata\.java\.specification\.vendor=.*/
assert buildProps =~ /build\.metadata\.java\.specification\.version=.*/
assert buildProps =~ /build\.metadata\.java\.vendor=.*/
assert buildProps =~ /build\.metadata\.java\.version=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.name=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.info=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.vendor=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.version=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.specification\.name=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.specification\.vendor=.*/
assert buildProps =~ /build\.metadata\.java\.vm\.specification\.version=.*/
assert buildProps =~ /build\.metadata\.sun\.management\.compiler=.*/
assert buildProps =~ /build\.metadata\.java\.opts=.*/
assert buildProps =~ /build\.metadata\.os\.family=.*/
assert buildProps =~ /build\.metadata\.os\.arch=.*/
assert buildProps =~ /build\.metadata\.os\.name=.*/
assert buildProps =~ /build\.metadata\.os\.version=.*/
assert buildProps =~ /build\.metadata\.execution\.cmdline=.*/
assert buildProps =~ /build\.metadata\.execution\.goals=.*/
assert buildProps =~ /build\.metadata\.username=.*/
assert buildProps =~ /build\.metadata\.sun\.management\.compiler=.*/
assert buildProps =~ /build\.metadata\.version=.*/
assert buildProps =~ /build\.metadata\.active\.profiles=.*/
assert buildProps =~ /build\.metadata\.execution\.opts=.*/
assert buildProps =~ /build\.metadata\.hostname=.*/

return true;
