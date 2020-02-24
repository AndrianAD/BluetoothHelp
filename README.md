# BluetoothHelp

 private lateinit var bluetoothHelp: BluetoothHelp


   override fun onResume() {
        bluetoothHelp = BluetoothHelp(this, this)
        super.onResume()
    }


  override fun onDestroy() {
        try {
            unregisterReceiver(bluetoothHelp.receiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
    
    implements BluetoothEvents
