#!/bin/bash

function usage()
{
    echo " Usage : "
    echo "   bash elec_run.sh deploy"
    echo "   bash elec_run.sh query    asset_account "
    echo "   bash elec_run.sh register asset_account asset_amount "
    echo "   bash elec_run.sh transfer from_asset_account to_asset_account amount "
    echo " "
    echo " "
    echo "examples : "
    echo "   bash elec_run.sh deploy "
    echo "   bash elec_run.sh register  1-2  4  447  1  "
    echo "   bash elec_run.sh register  Asset1  10000000 "
    echo "   bash elec_run.sh transfer  Asset0  Asset1 11111 "
    echo "   bash elec_run.sh query 1-2"
    echo "   bash elec_run.sh query Asset1"
    exit 0
}

    case $1 in
    deploy)
            [ $# -lt 1 ] && { usage; }
            ;;
    register)
            [ $# -lt 6 ] && { usage; }
            ;;
    trace)
            [ $# -lt 6 ] && { usage; }
            ;;
    query)
            [ $# -lt 2 ] && { usage; }
            ;;
    *)
        usage
            ;;
    esac

    java -Djdk.tls.namedGroups="secp256k1" -cp 'apps/*:conf/:lib/*' org.fisco.bcos.asset.client.ElecClient $@