# Metrics demo
The project show some of the capabilities of using JMX and the excellent project [Metrics](http://metrics.codahale.com/) to create instrumentation of your running JVM. 
By default it use the [jolokia](http://www.jolokia.org/) agent.
To get the example running just check it out from github [metricsdemo](https://github.com/thenodon/metricsdemo) and then run:   
```
$ mvn install  
$ mvn exec:exec  
```

Now you can just connect with [hawtio](http://hawt.io/) against the running JVM on localhost and port 7778.

# License
Copyright (c) 2014 Anders Håål

Published under Apache Software License 2.0, see [LICENSE](http://www.apache.org/licenses/LICENSE-2.0.html)
