import React, { Component } from 'react'
import {
    Navbar,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink} from 'reactstrap';
import { Link } from 'react-router-dom';

export default class Header extends Component {
  render() {
    return (
        <Navbar color="light" light expand="md">
        <NavbarBrand tag={Link} to="/">Gestion Entrepot</NavbarBrand>
          <Nav className="ml-auto" navbar>
            <NavItem>
              <NavLink href="/entrepots/">Entrepots</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/products/">Produits</NavLink>
            </NavItem>
          </Nav>
      </Navbar>
    )
  }
}
