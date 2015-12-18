Instructions on using the update center!

+-------------------------------------------------------------------------------------------------------------------+
|																													|
|																													|
|																													|
|   							 Parameters that are compulsory in the server.xml context 							|
|																													|
|																													|
|																													|
+-------------------------------------------------------------------------------------------------------------------+
|																													|
| 1.Path to the lib directory containing the necessary jar files													|
| <Environment name="serverlibdir" value="path\\to\\libs\\folder" type="java.lang.String" override="false"/>		|
|																													|
+-------------------------------------------------------------------------------------------------------------------+
|																													|
| 2.Path to the version directory containing the war files 	that will be updated									|
| <Environment name="versionsrootdir" value="path\\to\\versions" type="java.lang.String" override="false"/>			|
|																													|
+-------------------------------------------------------------------------------------------------------------------+
|																													|
| 3.Path to the root directory of the update center																	|
| <Environment name="updatecenterroot" value="path\\to\\root" type="java.lang.String" override="false"/>			|
|																													|
+-------------------------------------------------------------------------------------------------------------------+
|																													|
| 4.The prefix before the version build number																		|
| <Environment name="versionnumberprefix" value="-1.2-" type="java.lang.String" override="false"/>					|
|																													|
+-------------------------------------------------------------------------------------------------------------------+
|																													|
| 5.Path to the validation pattern property file																	|
| <Environment name="validationpatternpropertyfile" value="path\\to\prop" type="java.lang.String" override="false"/>|	
|																													|
+-------------------------------------------------------------------------------------------------------------------+



+-------------------------------------------------------------------------------------------------------------------+
|																													|
|																													|
|																													|
|   							 		Optional parameters in the server.xml context 								|
|																													|
|																													|
|																													|
+-------------------------------------------------------------------------------------------------------------------+
|																													|
| 6.The maximum size of each file chunk in bytes!!! 5 MByte is default!!!											|
| <Environment name="chunksize" value="5*1024*1024" type="java.lang.Long" override="false"/>						|	
|																													|
+-------------------------------------------------------------------------------------------------------------------+








