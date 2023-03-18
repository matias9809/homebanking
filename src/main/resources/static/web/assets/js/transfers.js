const { createApp } = Vue
createApp( {
    data(){
        return {
            count:0,
            client:[],
            accounts:[],
            loans:[],
            type_transactions:"",
            account_reciving:"receiving account",
            account_origin:"origin account",
            amount:"",
            description:"",
            id:undefined,
            amount:"",
            payment:"select payment",
        }
    },
    created(){
        this.load_data();
            axios.get(`/api/Loans`)
            .then(response=>{
                this.loans=response.data
                console.log(this.loans);
            })
            .catch(err=>console.log(err))
            
    },
    methods: {
        load_data(){
            axios.get(`/api/clients/current`)
            .then(response=>{
                this.client=response.data;
                if(this.client.account){
                    this.accounts=this.client.account
                }
            })
            .catch(err=>console.log(err))
        },

        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "./index.html")
            .catch(err=>console.log(err))
        },
        createTransaction(){
            axios.post('/api/transactions',`amount=${this.amount}&numberOrigin=${this.account_origin}&numberRecep=${this.account_reciving}&description=${this.description}`,{
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(()=>{
                    window.location.href = '/web/transfers.html';
                })
            .catch(err=>alert(err))
        },
        createLoan(){
            axios.post('/api/loans',{id_prestamo:this.id,amount:this.amount,payment:this.payment,numberAccount:this.account_origin},{
                headers:{
                    'Content-Type': 'application/json'
                }
            }).then(()=>{
                    window.location.href = '/web/transfers.html';
                })
            .catch(err=>alert(err))
        }
    },

} ).mount("#app")