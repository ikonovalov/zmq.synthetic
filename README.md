Prerequisite (install it first):
* https://github.com/zeromq/jzmq
* https://github.com/zeromq/libzmq

Used: 
* jzmq 3.1.1-SNAPSHOT
* zeroMQ 4.2.3
* OpenJDK Runtime Environment (Zulu 8.23.0.3-linux64) (build 1.8.0_144-b01)
* Linux 9f1e8b40de13 4.4.0-97-generic #120-Ubuntu SMP Tue Sep 19 17:28:18 UTC 2017 x86_64 x86_64 x86_64 GNU/Linux
* Host machine _(in my case)_:
    * Docker version 17.05.0-ce, build 89658be
    * Docker-compose version 1.9.0, build 2585387
    * CPU Intel(R) Core(TM) i5-4200U CPU @ 1.60GHz
    * SSD OCZ-VECTOR150
    * DDR3 Synchronous 1600 MHz Rockwell

```bash
 mvn clean package && docker-compose up
```