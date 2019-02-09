import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label,Row, Col, Badge } from 'reactstrap';

class EntrepotEdit extends Component {
  constructor(props) {
    super(props);
    this.state = {
      item : {
        id: null,
        name: '',
        capacite: 0,
        address: '',
        etat: null,
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
      const en = await (await fetch(`/entrepot/${id}`,{
        method: "GET",
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      })).json();
      if(id === 'new'){
        this.select.value = 'ON';
      }else{
        this.select.value = en.entrepot.etat ? 'ON' : 'OFF'
      }
      this.setState({item: {...en.entrepot,message: en.message,etat: en.entrepot.etat ? 'ON' : 'OFF'}});
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
    console.log(this.select.value,item.etat)

    item['etat'] = item['etat'] === 'ON' ? true : false;
    console.log(this.select.value,item.etat)

    await fetch(`/entrepots`, {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    })
    .then(res => res.json())
    .then(d => this.props.history.push({
      pathname: '/entrepots',
      state: {
        message: d.message
      }
    }))
    .catch(error => console.log(error))
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Entrepot' : 'Ajouter Entrepot'}</h2>;
    
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
            <FormGroup className="col-md-6 mb-6">
              <Label for="capacite">Capacite</Label>
              <Input type="number" min='0' name="capacite" id="capacite" value={item.capacite}
                     onChange={this.handleChange} autoComplete="address-level1" required/>
            </FormGroup>
            <FormGroup className="col-md-6 mb-6">
              <Label for="etat">Etat</Label>
              <Input innerRef={ ref => this.select = ref} type="select" name="etat" id="etat" onChange={this.handleChange}>
                <option value='ON'>ON</option>
                <option value='OFF'>OFF</option>
              </Input>
            </FormGroup>
          </div>
          <FormGroup>
            <Label for="address">Adresse</Label>
            <Input type="text" name="address" id="address" value={item.address}
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

export default withRouter(EntrepotEdit);