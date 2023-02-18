const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            count:0,
            client:undefined,
            account:undefined,
            loan:undefined,
            cards:undefined,
            cards_credit:undefined,
            cards_debit:undefined
        }
    },
    created(){
        axios.get(`http://localhost:8080/api/clients/1`)
        .then(response=>{
            this.client=response.data;
            if(this.client.loan){
                this.loans=this.client.loan;
            }
            this.cards=response.data.cards
            this.cards_debit=this.cards.filter(cards=>cards.type=='DEBIT')
            this.cards_credit=this.cards.filter(cards=>cards.type=='CREDIT')
            console.log(this.client);
            console.log(this.loans)
            console.log(this.cards_credit)
            console.log(this.cards_credit)
        })
        .catch(err=>console.log(err))
        axios.get(`http://localhost:8080/api/account`)
        .then(response=>{
            this.account=response.data;
            console.log(this.account)
        })
        .catch(err=>console.log(err))
    },
    methods: {
        load_data:function(){
            axios.get(`http://localhost:8080/api/clients/1`)
            .then(response=>{
                this.client=response.data;
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
        }
    },

} ).mount("#app")