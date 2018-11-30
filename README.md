# JSG : Java Stack Trace Grouper

A simple program that groups threads in jstack output with the same stack trace. The following is some example jstack output.

```
"org.eclipse.jdt.internal.ui.text.JavaReconciler" #401 daemon prio=1 os_prio=0 tid=0x00007fc207743800 nid=0x7e58 in Object.wait() [0x00007fc15e6f5000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at org.eclipse.jface.text.reconciler.AbstractReconciler$BackgroundThread.run(AbstractReconciler.java:180)
	- locked <0x00000000c1800210> (a org.eclipse.jface.text.reconciler.DirtyRegionQueue)

"org.eclipse.jdt.internal.ui.text.JavaReconciler" #225 daemon prio=1 os_prio=0 tid=0x00007fc206ebc800 nid=0x78f3 in Object.wait() [0x00007fc15c135000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at org.eclipse.jface.text.reconciler.AbstractReconciler$BackgroundThread.run(AbstractReconciler.java:180)
	- locked <0x00000000ca983320> (a org.eclipse.jface.text.reconciler.DirtyRegionQueue)

"org.eclipse.jdt.internal.ui.text.JavaReconciler" #219 daemon prio=1 os_prio=0 tid=0x00007fc2052ed800 nid=0x78c3 in Object.wait() [0x00007fc15f74c000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at org.eclipse.jface.text.reconciler.AbstractReconciler$BackgroundThread.run(AbstractReconciler.java:180)
	- locked <0x00000000c2080190> (a org.eclipse.jface.text.reconciler.DirtyRegionQueue)
```

Running this through `jsg.sh` produces the following output.

```
"org.eclipse.jdt.internal.ui.text.JavaReconciler" #401 daemon prio=1 os_prio=0 tid=0x00007fc207743800 nid=0x7e58 in Object.wait() [0x00007fc15e6f5000]
"org.eclipse.jdt.internal.ui.text.JavaReconciler" #225 daemon prio=1 os_prio=0 tid=0x00007fc206ebc800 nid=0x78f3 in Object.wait() [0x00007fc15c135000]
"org.eclipse.jdt.internal.ui.text.JavaReconciler" #219 daemon prio=1 os_prio=0 tid=0x00007fc2052ed800 nid=0x78c3 in Object.wait() [0x00007fc15f74c000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
	at java.lang.Object.wait(Native Method)
	at org.eclipse.jface.text.reconciler.AbstractReconciler$BackgroundThread.run(AbstractReconciler.java:180)
	- locked <HEX> (a org.eclipse.jface.text.reconciler.DirtyRegionQueue)
```

## Building

The following command will build an executable on Linux.

```
mvn package
cat src/main/scripts/stub.sh ./target/jsg-0.0.1-SNAPSHOT.jar > jsg.sh
chmod +x jsg.sh

```

## Running

If `jsg.sh` is on your path then you can run as follows.

```
jstack <java process id> | jsg.sh
```



