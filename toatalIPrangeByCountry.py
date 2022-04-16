
import json
import sys
import os


import os


requestJson={
          "query": {
                  "match": { "column3" : "IN"}
                    },
                      "size": 7000
                      }

cmd="curl http://localhost:5106/passerine_ip2location_sample/_search -d '" + json.dumps(requestJson)  + "'"
print("\ncommand:" + cmd)
result = os.popen(cmd).read()
