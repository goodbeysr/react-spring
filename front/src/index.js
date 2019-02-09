import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './app.css'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import Home from './pages/Home'
import Entrepots from './pages/Entropots'
import EntrepotEdit from './pages/EntrepotEdit'
import ProductsEdit from './pages/ProductsEdit'
import Products from './pages/Products'
import EntrepotProducts from './pages/EntrepotProducts'
import EntrepotProductsEdit from './pages/EntrepotProductsEdit'
import NoMatch from './pages/NoMatch'
import Header from './components/Header'

const App = () => (
    <Router>
        <div>
            <Header/>
            <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/entrepots/' exact={true} component={Entrepots}/>
            <Route path='/entrepots/:id' component={EntrepotEdit}/>
            <Route path='/products/' exact component={Products}/>
            <Route path='/products/:id' exact component={ProductsEdit}/>
            <Route path='/entrepotProducts/' exact component={EntrepotProducts}/>
            <Route path='/entrepotProducts/:id' exact component={EntrepotProductsEdit}/>
            <Route component={NoMatch} />
            </Switch>
        </div>
    </Router>
  )

ReactDOM.render(<App />, document.getElementById('root'));
