import React, { Component } from 'react'
import { Button, ButtonGroup, Container, Table,Badge, Row, Col } from 'reactstrap';
import { Link } from 'react-router-dom';


export default class Entropots extends Component {
    constructor(props) {
        super(props);
        this.state = {entrepots: [], isLoading: true, message: ""};
      }
    
      async componentDidMount() {
        this.setState({
          message: this.props.location.state ? this.props.location.state.message : '' ,
          isLoading: true
        })
    
        await fetch('/entrepots')
          .then(response => response.json())
          .then(data => this.setState({entrepots: data.entrepots, isLoading: false}))
          .catch(console.log)
      }
    
      remove = async (id) =>{
        if(window.confirm('Vous voulez supprimer l\'entrepot '+id)){
          await fetch(`/entrepots/${id}`, {
            method: 'DELETE',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
          }).then( r => r.json()).then( r => this.setState({message: r.message})).then(() => {
            let updatedEntrepots = [...this.state.entrepots].filter(i => i.id !== id);
            this.setState({entrepots: updatedEntrepots});
          }).catch(console.log);
        }
      }

      of = async (id) => {
        if(window.confirm('Vous voulez switcher l\'etat de l\'entrepot '+id)){
          await fetch(`/entrepots/etat/${id}`, {
            method: 'PUT',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            }
          }).then(r => r.json()).then( r => {
            console.log(r);
            this.setState({message: r.message});
          }).then(() => {
            let updatedEntrepots = [...this.state.entrepots].map( i => {
              if(i.id === id){
                return {...i,etat: !i.etat};
              }else{
                return i;
              }
            });
            this.setState({entrepots: updatedEntrepots});
          });
        }
      }

      handleBadgeClick = (e,id) => {
        e.preventDefault();
        this.props.history.push({
          pathname: '/entrepotProducts/',
          state: {
            id: id
          }
        })
      }
    
      render() {
        const {entrepots, isLoading} = this.state;
    
        if (isLoading) {
          return <p>Loading...</p>;
        }
    
        const List = entrepots.map(e => {
          return (
            <tr key={e.id}>
            <td style={{whiteSpace: 'nowrap'}}><Badge tag={Link} to='' onClick={ ev => this.handleBadgeClick(ev,e.id)} color="primary" pill>{e.id}</Badge></td>
            <td style={{whiteSpace: 'nowrap'}}>{e.name}</td>
            <td style={{whiteSpace: 'nowrap'}}>{e.address}</td>
            <td style={{whiteSpace: 'nowrap'}}>{e.capacite}</td>
            <td style={{whiteSpace: 'nowrap'}}>{e.etat ? 'ON' : 'OFF'}</td>
            <td>
              <ButtonGroup>
                <Button size="sm" color="success" tag={Link} to={"/entrepots/" + e.id}>Modifier</Button>
                <Button size="sm" color="warning" onClick={() => this.of(e.id)}>O/F</Button>
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
                <Button color="primary" tag={Link} to="/entrepots/new">Ajouter Entrepot</Button>
              </div>
              <h3>Les entrepots</h3>
              <Row>
                <Col sm={{ size: 6, order: 2, offset: 4 }}><h2>{this.state.message !== '' ? <Badge color="info">{this.state.message}</Badge> : null}</h2></Col>
              </Row>
              <Table className="mt-4">
                <thead>
                <tr>
                  <th width="10%">id</th>
                  <th width="10%">intitule</th>
                  <th>adresse</th>
                  <th>Capacité</th>
                  <th>Etat</th>
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
