import React, { Component } from 'react'
import { Button, Container, Form, FormGroup, Input, Label, Row, Col, Badge } from 'reactstrap';
import { withRouter } from 'react-router-dom';

class EntrepotProductsEdit extends Component {
  constructor(props) {
    super(props);
    this.state = {
      item : {
        entrepot: null,
        product: null,
        capacite: 0
      },
      products: [],
      message: ''
    };
  }
  async componentDidMount() {
    const idP = this.props.match.params.id;
    const idE = this.props.location.state.id;

    let entrepot,capacite,product,products;

    entrepot = await fetch(`/entrepot/${idE}`).then(res => res.json()).then(res => res.entrepot)
    products = await fetch(`/products`).then(res => res.json()).then(res => res.products)
    if (idP !== 'new') {
      capacite = await fetch(`/entrepot/${idE}/product/${idP}`).then(res => res.json()).then(res => res.capacite)
      product = idP
    }else{
      product = products[0].id
    }
    let st = this.state;
    this.setState({
      products,
      item: {
        entrepot: entrepot,
        product: product,
        capacite: st.item.capacite | capacite
      }
    })
  }
  handleSubmit = async (event) => {
    event.preventDefault();
    let { item : {
      capacite,
      entrepot,
      product
    } } = this.state;
    console.log(capacite,entrepot,product)
    await fetch(`/addEntrepotProduct/${entrepot.id}/${product}`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(capacite),
    })
    .then(res => res.json())
    .then(res => this.props.history.push({
      pathname: '/entrepotProducts',
      state: {
        id: this.state.item.entrepot.id,
        message: res.message
      }
    }))
    .catch(error => console.log(error))
  }
  handleChange = (event) => {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    console.log(item)
    this.setState({item});
  }
  render() {
    const { item, products } = this.state;
    const title = <h2>{item.id ? 'Edit Product' : 'Ajouter Product'}</h2>;

    const List = products.map( e => (
      <option key={e.id} value={e.id}>{e.name}</option>
    ))

    return (
      <Container>
        {title}
        <Row>
            <Col sm={{ size: 6, order: 2, offset: 4 }}><h2>{this.state.message !== '' ? <Badge color="info">{this.state.message}</Badge> : null}</h2></Col>
        </Row>
        <Form onSubmit={this.handleSubmit}>
          <div className="row">
            <FormGroup className="col-md-6 mb-6">
              <Label for="entrepot">Entrepot</Label>
              <Input type="text" name="entrepot" id="entrepot" value={item.entrepot ? item.entrepot.name : ''} autoComplete="address-level1" disabled/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <Label for="product">Product</Label>
              <Input innerRef={ ref => this.select = ref} type="select" name="product" id="product" onChange={this.handleChange} disabled={this.props.match.params.id === 'new' ? false : true}>
                {List}
              </Input>
            </FormGroup>
          </div>
          <FormGroup className="col-md-4 mb-4">
            <Label for="capacite">Capacite</Label>
            <Input type="number" min='0' name="capacite" id="capacite" value={item.capacite}
                onChange={this.handleChange} autoComplete="address-level1" required/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Enregistrer</Button>
            <Button color="secondary" onClick={() => this.props.history.goBack()}>Cancel</Button>
        </FormGroup>
        </Form>
      </Container>
    )
  }
}

export default withRouter(EntrepotProductsEdit)