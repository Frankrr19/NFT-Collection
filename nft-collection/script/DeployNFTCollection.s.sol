// SPDX-License-Identifier: MIT

pragma solidity ^0.8.24;

import {Script} from "../lib/forge-std/src/Script.sol";
import {VEXCollection} from "../src/VEXCollection.sol";

contract DeployNFTCollection is Script  {
    function run () external returns (VEXCollection) {

        uint256 deployerPrivateKey = vm.envUint("PRIVATE_KEY");
        vm.startBroadcast(deployerPrivateKey);

        string memory name_ = "VEX Collection"; 
        string memory symbol_ = "VEX";
        uint256 totalSupply_ = 2;
        string memory baseUri_ = "ipfs://bafybeiahtxroxsf7j53qnanzgav7x3sjkzrj5brydw4zjxxgamamyqh2cm/";
        VEXCollection vexCollection = new VEXCollection(name_, symbol_, totalSupply_, baseUri_);

        vm.stopBroadcast();
        
        return vexCollection;

    }
   
}