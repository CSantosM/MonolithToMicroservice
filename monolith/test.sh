

#!/bin/bash

function test() {

    output=$(curl -I $3 2>/dev/null | head -n 1|cut -d$' ' -f2)
	if [[ "GET" == *$2* ]]
    then
        output=$(curl -I $3 2>/dev/null | head -n 1|cut -d$' ' -f2)
    else
        output=$(curl -i -H "Accept: application/json" -H "Content-type: application/json" \
					-X POST -d '{"customerId":"2","productId":"3", "productQuantity": "1"}' \
					$3 2>/dev/null | head -n 1|cut -d$' ' -f2)
    fi


    if [[ $output == *$4* ]]
    then
        echo $1": OK"
    else
        echo $1:" FAIL"
    fi
}


HOST=localhost
PORT=8080


test "GET products /products/:  " "GET" "http://$HOST:$PORT/products/" "200"

test "GET product /products/3:  " "GET" "http://$HOST:$PORT/products/3" "200"

test "GET customers /customers/:" "GET" "http://$HOST:$PORT/customers" "200"

test "GET customer /customers/1:" "GET" "http://$HOST:$PORT/customers" "200"

test "POST order /orders/" "POST" "http://$HOST:$PORT/orders/" "200"

test "POST order /orders/" "POST" "http://$HOST:$PORT/orders/" "406"



echo "Restart database for test again"