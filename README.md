# GKD

I created Peter-bochs debugger for os dev. I renamed it to GKD because peter-bochs only supports bochs but GKD support qemu too. The reasons I created it are:

bochs internal debugger is hard to dump out some x86 data structure, i need a faster way to do it.
tired with command line
i need a way to profiling part of my kernel, some bugs are really hard to trace without this feature
source level debug support

# Documents:

https://github.com/mcheung63/GKD/wiki

## Compile:

Here are steps to compile GKD. GKD is depends on some my other projects, all are open source and written in Java & maven. So just do “mvn clean install” in every of them.

Check out these projects in order, run “mvn clean install” on every of them:

1. https://github.com/mcheung63/peter-swing
2. https://sourceforge.net/projects/tightvncpanel/
3. https://sourceforge.net/projects/peter-ar/
4. https://sourceforge.net/projects/peter-dwarf/

Finally, git clone GKD source from https://github.com/mcheung63/GKD.git

1) execute the command in installJar.txt
2) run “mvn clean package”, you will have a GKDxxxxx.jar in your target folder, now you are ready to run it.
3) download this
4) cd to the unzipped folder, run “java -jar GKD-20150124.jar -f gkd_bochs.xml”
5) read gkd_bochs.xml, then you know everything.


## Gossip:

1) "mvn versions:display-dependency-updates" to check outdated dependencies
2) "mvn graph:reactor" to generate maven dependency graph

To run GKD with profiling feature, compile bochs with this config:

1) copy folder instrument/ to <bochs source>/instrument/gkd
2) ./configure --enable-instrumentation=instrument/gkd --prefix=/toolchain/ --enable-debugger --enable-disasm --disable-debugger-gui --with-rfb --disable-readline --with-sdl2 --enable-all-optimizations --enable-fpu --enable-show-ips

## Screens

![Main screen](http://peter.kingofcoders.com/wp-content/uploads/2012/11/Screenshot-Peter-Bochs-Debugger-20111207.png)

![C++ profiling](http://peter.kingofcoders.com/wp-content/uploads/2012/11/螢幕快照-2015-07-31-上午2.29.52.png)

Contact:

Peter (mcheung63@hotmail.com) 
