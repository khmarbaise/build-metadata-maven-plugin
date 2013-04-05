File file = new File( basedir, "build.log" );
assert file.exists();

String text = file.getText("utf-8");

assert text =~ /\[DEBUG\] define property maven\.version = "(.*)"/

assert text.contains("[echo] maven.version=")
return true;
