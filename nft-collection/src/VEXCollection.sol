// SPDX-License-Identifier: MIT

pragma solidity ^0.8.24;

import "../lib/openzeppelin-contracts/contracts/token/ERC721/ERC721.sol";
import {Strings} from "../lib/openzeppelin-contracts/contracts/utils/Strings.sol";

contract VEXCollection is ERC721{
    using Strings for uint256;

    uint256 public currentTokenId;
    uint256 public totalSupply;
    string public baseUri;

    event MintNFT(address userAddress_, uint256 tokenId_);
    constructor(string memory _name, string memory _symbol, uint256 _totalSupply, string memory _baseUri) ERC721(_name, _symbol) {
        totalSupply = _totalSupply;
        baseUri = _baseUri;
    }

    function mint() external{
        require(currentTokenId < totalSupply, "VEXCollection: Sold Out");
        _safeMint(msg.sender, currentTokenId);
        uint256 id = currentTokenId;
        currentTokenId++;
        emit MintNFT(msg.sender, id);
    }

    function _baseURI() internal override view virtual returns (string memory) {
        return baseUri;
    }

    function tokenURI(uint256 tokenId) public override view virtual returns (string memory) {
        _requireOwned(tokenId);

        string memory baseURI = _baseURI();
        return bytes(baseURI).length > 0 ? string.concat(baseURI, tokenId.toString(), ".json") : "";
    }

    
}