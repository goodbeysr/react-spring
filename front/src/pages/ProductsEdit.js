import React, { Component } from 'react'
import { Button, Container, Form, FormGroup, Input, Label, Row, Col, Badge } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class ProductsEdit extends Component {
    constructor(props) {
        super(props);
        this.state = {
          item : {
            id: null,
            name: '',
            price: 0,
            quantite: 0,
            volume: 0,
            createdAt: null
          },
          message: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }
      async componentDidMount() {
        const id = this.props.match.params.id;
        if (id !== 'new') {
        const en = await (await fetch(`/product/${id}`,{
            method: "GET",
            headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
            }
        })).json();
        this.setState({item: en.product,message: en.message});
        }
      }
      handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
      }
    async handleSubmit(event) {
        event.preventDefault();
        let { item } = this.state;
        console.log(this.state)
        await fetch(`/products`, {
          method: (item.id) ? 'PUT' : 'POST',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(item),
        })
        .then(res => res.json())
        .then(d => this.props.history.push({
          pathname: '/products',
          state: {
            message: d.message
          }
        }))
        .catch(error => console.log(error))
      }
    render() {
        const { item } = this.state;
        const title = <h2>{item.id ? 'Edit Produit' : 'Ajouter Produit'}</h2>;
        
        return (
          <Container>
            {title}
            <Row>
                <Col sm={{ size: 6, order: 2, offset: 4 }}><h2>{this.state.message !== '' ? <Badge color="info">{this.state.message}</Badge> : null}</h2></Col>
            </Row>
            <Form onSubmit={this.handleSubmit}>
              <FormGroup>
                <Label for="name">Intitule</Label>
                <Input type="text" name="name" id="name" value={item.name}
                       onChange={this.handleChange} autoComplete="name" required/>
              </FormGroup>
              <div className="row">
                <FormGroup className="col-md-4 mb-4">
                  <Label for="price">Prix</Label>
                  <Input type="number" min='0' name="price" id="price" value={item.price}
                         onChange={this.handleChange} autoComplete="address-level1" required/>
                </FormGroup>
                <FormGroup className="col-md-4 mb-4">
                  <Label for="quantite">Quantite</Label>
                  <Input type="number" min='0' name="quantite" id="quantite" value={item.quantite}
                         onChange={this.handleChange} autoComplete="address-level1" required/>
                </FormGroup>
                <FormGroup className="col-md-4 mb-4">
                  <Label for="volume">Volume</Label>
                  <Input type="number" min='0' name="volume" id="volume" value={item.volume}
                         onChange={this.handleChange} autoComplete="address-level1" required/>
                </FormGroup>
              </div>
              <FormGroup>
                <Button color="primary" type="submit">Enregistrer</Button>
                <Button color="secondary" onClick={() => this.props.history.goBack()}>Cancel</Button>
            </FormGroup>
            </Form>
          </Container>
        )
      }
}

export default withRouter(ProductsEdit)