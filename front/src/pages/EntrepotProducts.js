import React, { Component } from 'react'
import { withRouter} from 'react-router-dom';
import { Button, ButtonGroup, Container, Table,Badge, Row, Col, FormGroup } from 'reactstrap';

class EntrepotProducts extends Component {
    constructor(props) {
        super(props);
        this.state = {products: [],id: null,isLoading: true,message: this.props.location.state ? this.props.location.state.message : ''};
      }

      async componentDidMount() {
        
        if(this.props.location.state){
          const id = this.props.location.state.id;
          const res = await (await fetch(`/entrepot/products/${id}`,{
            method: "GET",
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
        })).json();
        console.log(res.products.entrepotProduct)
        this.setState({products: res.products.entrepotProduct,isLoading:false,id:id});
        }else{
          this.props.history.push('/entrepots')
        }
      }
      handleClick = (ev,id,type) => {
        ev.preventDefault()
        const path= type === 'add' ? 'new' : id; 
        this.props.history.push({
          pathname: '/entrepotProducts/'+path,
          state: {
            id: this.state.id
          }
        })
      }
      remove = async (id,capacite) =>{
        if(window.confirm('Vous voulez supprimer ce le produit '+id)){
          await fetch(`/deleteEntrepotProduct/${this.props.location.state.id}/${id}`, {
            method: 'DELETE',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(capacite)
          }).then( r => r.json()).then( r => this.setState({message: r.message})).then(() => {
            let updatedProducts = [...this.state.products].filter(i => i.product.id !== id);
            this.setState({products: updatedProducts});
          }).catch(console.log);
        }
      }
    render() {
        const {products, isLoading} = this.state;
    
        if (isLoading) {
          return <p>Loading...</p>;
        }
    
        const List = products.map(element => {
          const { product: e, capacite } = element; 
          const date = new Date(e.createdAt);
          return (
            <tr key={e.id}>
            <td style={{whiteSpace: 'nowrap'}}><Badge color="primary" pill>{e.id}</Badge></td>
            <td style={{whiteSpace: 'nowrap'}}>{e.name}</td>
            <td style={{whiteSpace: 'nowrap'}}>{e.volume}</td>
            <td style={{whiteSpace: 'nowrap'}}>{e.quantite}</td>
            <td style={{whiteSpace: 'nowrap'}}>{e.price}</td>
            <td style={{whiteSpace: 'nowrap'}}>{capacite}</td>
            <td style={{whiteSpace: 'nowrap'}}>{new Intl.DateTimeFormat('eu-AU').format(date)}</td>
            <td>
              <ButtonGroup>
                <Button size="sm" color="success" onClick={ ev => this.handleClick(ev,e.id,'edit')}>Modifier</Button>
                <Button size="sm" color="danger" onClick={() => this.remove(e.id,capacite)}>Supprimer</Button>
              </ButtonGroup>
            </td>
          </tr>
          )
        });
    
        return (
          <div>
            <Container fluid>
              <div className="float-right">
                <FormGroup>
                <Button color="secondary" onClick={() => this.props.history.goBack()}>Retour</Button>
                <Button color="primary" onClick={ ev => this.handleClick(ev,null,'add')}>Ajouter Produit</Button>
                </FormGroup>
              </div>
              <h3>{`Entrepot ${this.props.location.state.id}`}</h3>
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
                  <th>Capacite</th>
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


export default withRouter(EntrepotProducts);