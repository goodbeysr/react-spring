import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button, ButtonGroup, Container, Table,Badge, Row, Col } from 'reactstrap';



class Products extends Component {
  constructor(props) {
    super(props);
    this.state = {products: [], isLoading: true,message: ''};
  }

  async componentWillMount() {
    this.setState({
      message: this.props.location.state ? this.props.location.state.message : '' ,
      isLoading: true
    })
    await fetch('/products')
          .then(response => response.json())
          .then(data => this.setState({products: data.products, isLoading: false}))
          .catch(console.log)
  }

  remove = async (id) => {
    if(window.confirm('Vous voulez supprimer l\'product '+id)){
      await fetch(`/products/${id}`, {
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).then( r => r.json()).then( r => this.setState({message: r.message})).then(() => {
        let updatedProducts = [...this.state.products].filter(i => i.id !== id);
        this.setState({products: updatedProducts});
      }).catch(console.log);
    }
  }

  render() {
    const {products, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const List = products.map(e => {
      const date = new Date(e.createdAt);
      return (
        <tr key={e.id}>
        <td style={{whiteSpace: 'nowrap'}}><Badge color="primary" pill>{e.id}</Badge></td>
        <td style={{whiteSpace: 'nowrap'}}>{e.name}</td>
        <td style={{whiteSpace: 'nowrap'}}>{`${e.volume} Kg`}</td>
        <td style={{whiteSpace: 'nowrap'}}>{e.quantite}</td>
        <td style={{whiteSpace: 'nowrap'}}>{`${e.price} DHS`}</td>
        <td style={{whiteSpace: 'nowrap'}}>{new Intl.DateTimeFormat('eu-AU').format(date)}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="success" tag={Link} to={"/products/" + e.id}>Modifier</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(e.id)}>Supprimer</Button>
          </ButtonGroup>
        </td>
      </tr>
      )
    });

    return (
      <div>
        <Container fluid>
          <div className="float-right">
            <Button color="primary" tag={Link} to="/products/new">Ajouter Produit</Button>
          </div>
          <h3>Les produits</h3>
          <Row>
            <Col sm={{ size: 6, order: 2, offset: 4 }}><h2>{this.state.message !== '' ? <Badge color="info">{this.state.message}</Badge> : null}</h2></Col>
          </Row>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="10%">id</th>
              <th width="10%">intitule</th>
              <th>volume</th>
              <th>quantite</th>
              <th>Prix stock</th>
              <th>Date creation</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {List}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default Products;