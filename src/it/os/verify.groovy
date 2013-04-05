File file = new File( basedir, "build.log" );
assert file.exists();

String text = file.getText("utf-8");

assert text =~ /\[DEBUG\] define property build\.environment\.os\.name = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.environment\.os\.family = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.environment\.os\.version = "(.*)"/
assert text =~ /\[DEBUG\] define property build\.environment\.os\.arch = "(.*)"/

assert text.contains("[echo] build.environment.os.name=")
assert text.contains("[echo] build.environment.os.version=")
assert text.contains("[echo] build.environment.os.family=")
assert text.contains("[echo] build.environment.os.arch=")
return true;
