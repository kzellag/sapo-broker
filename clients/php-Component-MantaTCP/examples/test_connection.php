#!/servers/php/bin/php -q

<?php
#!/usr/bin/php -q
include('../classes/manta.php');
set_time_limit(0);
error_reporting(1);

echo "\nTesting streams\n";
$broker=new SAPO_Manta(array('debug'=>TRUE,'force_streams'=>TRUE,'force_dom'=>TRUE));

echo "\nTesting sockets\n";
$broker=new SAPO_Manta(array('debug'=>TRUE,'force_sockets'=>TRUE));


?>
