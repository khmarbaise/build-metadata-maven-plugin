File file = new File( basedir, "build.log" );
assert file.exists();

String text = file.getText("utf-8");

assert text =~ /\[DEBUG\] define property build\.environment\.username = "(.*)"/

assert text.contains("[INFO] build.environment.username=")
return true;
