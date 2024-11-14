1. Create a JAR from this project
2. Copy the compiled JAR file to Ranger Admin's classpath typically the $RANGER_HOME/lib/ directory
3. Configure Ranger to use your Custom Handler. Update the `ranger-admin-site.xml` file to point to your custom authentication handler.

```
<property>
  <name>ranger.authentication.handler</name>
  <value>auth.rancher.CustomTLSAuthProvider</value>
  <description>Custom Authentication Handler</description>
</property>
```

4. Start/Restart the Ranger Admin service to load the new configuration and the custom authentication handler

```
$RANGER_HOME/lib/ranger-admin start
```